package com.github.andreyjodar.backend.services.implement;

import java.time.LocalDateTime;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.andreyjodar.backend.core.security.AuthUserProvider;
import com.github.andreyjodar.backend.models.dtos.filter.BidFilterDTO;
import com.github.andreyjodar.backend.models.dtos.request.BidCreationDTO;
import com.github.andreyjodar.backend.models.dtos.response.AuctionBidUpdateResponse;
import com.github.andreyjodar.backend.models.dtos.response.SimpleMessageResponse;
import com.github.andreyjodar.backend.models.entities.Auction;
import com.github.andreyjodar.backend.models.entities.Bid;
import com.github.andreyjodar.backend.models.entities.User;
import com.github.andreyjodar.backend.repositories.BidRepository;
import com.github.andreyjodar.backend.services.interfaces.AuctionService;
import com.github.andreyjodar.backend.services.interfaces.BidService;
import com.github.andreyjodar.backend.services.specification.BidSpecification;
import com.github.andreyjodar.backend.shared.errors.BusinessException;
import com.github.andreyjodar.backend.shared.errors.NotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BidServiceImpl implements BidService {
    private static final String DETAIL_DESTINATION = "/topic/auction/";
    private static final String LIST_DESTINATION = "/topic/auction/list";
    private final BidRepository bidRepository;
    private AuctionService auctionService;
    private final AuthUserProvider authUserProvider;
    private final MessageSource messageSource;
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    @Transactional(readOnly = true)
    public Bid findById(Long id) {
        return bidRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.bids.notfound",
                new Object[] { id }, LocaleContextHolder.getLocale())));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Bid> findFiltered(BidFilterDTO bidFilterDTO, Pageable pageable) {
        Specification<Bid> spec = BidSpecification.buildFilter(bidFilterDTO);
        return bidRepository.findAll(spec, pageable);
    }

    @Override
    @Transactional
    public Bid create(BidCreationDTO bidCreationDTO) {
        User authUser = authUserProvider.getAuthUser();
        Auction auction = auctionService.findById(bidCreationDTO.getAuctionId());
        auctionService.validateActive(auction);

        validateAuctionPeriod(auction);
        validateAuctionBidPrice(bidCreationDTO.getBidPrice(), auction);
        validateNotOwner(authUser, auction);

        Auction updatedAuction = auctionService.updatePrice(auction, authUser.getEmail(), bidCreationDTO.getBidPrice());
        Bid createdBid = bidRepository.save(new Bid(bidCreationDTO.getBidPrice(), authUser, updatedAuction));

        messagingTemplate.convertAndSend(
            DETAIL_DESTINATION + updatedAuction.getId().toString(), 
            new AuctionBidUpdateResponse(
            updatedAuction.getId(), 
            updatedAuction.getCurrentPrice(), 
            updatedAuction.getCurrentBidder()
        ));

        messagingTemplate.convertAndSend(
            LIST_DESTINATION, 
            new SimpleMessageResponse(
            updatedAuction.getId(),
            "BID_CREATION"
        ));

        return createdBid;
    }

    private void validateNotOwner(User authUser, Auction auction) {
        if(authUser.getId().equals(auction.getAuctioneer().getId())) {
            throw new BusinessException(messageSource.getMessage("exception.bids.auctionowner",
                new Object[] { authUser.getId() }, LocaleContextHolder.getLocale()));
        }
    }

    private void validateAuctionPeriod(Auction auction) {
        LocalDateTime currentDatetime = LocalDateTime.now();
        if(currentDatetime.isBefore(auction.getStartDateTime()) || currentDatetime.isAfter(auction.getEndDateTime())) {
            throw new BusinessException(messageSource.getMessage("exception.bids.invalidperiod",
                new Object[] { auction.getStartDateTime(), auction.getEndDateTime() }, LocaleContextHolder.getLocale()));
        }
    }

    private void validateAuctionBidPrice(Double bidPrice, Auction auction) {
        if(bidPrice <= auction.getCurrentPrice()) {
            throw new BusinessException(messageSource.getMessage("exception.bids.bidpricelow",
                new Object[] { auction.getCurrentPrice() }, LocaleContextHolder.getLocale()));
        }
    }
    
}
