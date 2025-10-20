// package com.github.andreyjodar.backend.features.auction.service;

// import java.time.LocalDateTime;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.context.MessageSource;
// import org.springframework.context.i18n.LocaleContextHolder;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.jpa.domain.Specification;
// import org.springframework.stereotype.Service;

// import com.github.andreyjodar.backend.features.auction.mapper.AuctionMapper;
// import com.github.andreyjodar.backend.features.auction.model.Auction;
// import com.github.andreyjodar.backend.features.auction.model.AuctionFilterRequest;
// import com.github.andreyjodar.backend.features.auction.model.AuctionCreateRequest;
// import com.github.andreyjodar.backend.features.auction.model.AuctionEditRequest;
// import com.github.andreyjodar.backend.features.auction.model.AuctionStatus;
// import com.github.andreyjodar.backend.features.auction.repository.AuctionRepository;
// import com.github.andreyjodar.backend.features.bid.repository.BidRepository;
// import com.github.andreyjodar.backend.features.category.model.Category;
// import com.github.andreyjodar.backend.features.category.service.CategoryService;
// import com.github.andreyjodar.backend.features.user.model.User;
// import com.github.andreyjodar.backend.shared.errors.BusinessException;
// import com.github.andreyjodar.backend.shared.errors.ForbiddenException;
// import com.github.andreyjodar.backend.shared.errors.NotFoundException;

// @Service
// public class AuctionService {

//     @Autowired
//     private AuctionRepository auctionRepository;
//     @Autowired 
//     private CategoryService categoryService;
//     @Autowired
//     private AuctionMapper auctionMapper;
//     @Autowired
//     private MessageSource messageSource;
//     @Autowired
//     private BidRepository bidRepository;

//     public Auction createAuction(User authUser, AuctionCreateRequest auctionRequest) {
//         validatePeriod(auctionRequest.getStartDateTime(), auctionRequest.getEndDateTime());
//         Auction auction = auctionMapper.fromDto(auctionRequest);
//         validateOperation(authUser, auction);
//         initializePrice(auction, auctionRequest.getMinBid());
//         Category category = categoryService.findById(auctionRequest.getCategoryId());
//         fillComplexAttributes(auction, category, authUser);
//         auction.setStatus(AuctionStatus.ACTIVE);
//         return auctionRepository.save(auction);
//     }

//     public Auction updateAuction(Long id, User authUser, AuctionEditRequest auctionRequest) {
//         validatePeriod(auctionRequest.getStartDateTime(), auctionRequest.getEndDateTime());
//         Auction auction = findById(id);
//         validateOperation(authUser, auction);
//         Category category = categoryService.findById(auctionRequest.getCategoryId());
//         auction = auctionMapper.updateAuction(auction, auctionRequest);
//         auction.setCategory(category);
//         return auctionRepository.save(auction);
//     }

//     public void cancelAuction(Long id, User authUser) {
//         Auction auction = findById(id);
//         validateOperation(authUser, auction);
//         auction.setStatus(AuctionStatus.CANCELED);
//         auctionRepository.save(auction); 
//     }

//     public void activeAuction(Long id, User authUser) {
//         Auction auction = findById(id);
//         validateOperation(authUser, auction);
//         auction.setStatus(AuctionStatus.CANCELED);
//         auctionRepository.save(auction); 
//     }

//     public void deleteAuction(Long id, User authUser) {
//         Auction auction = findById(id);
//         validateOperation(authUser, auction);
//         validateDelete(auction);
//         auctionRepository.delete(auction); 
//     }

//     public void updatePrice(Auction auction, Float newPrice) {
//         if(newPrice <= auction.getMinBid() + auction.getIncrementValue()) {
//             throw new BusinessException(messageSource.getMessage("exception.auctions.invalidprice",
//                 new Object[] { auction.getMinBid() + auction.getIncrementValue() }, LocaleContextHolder.getLocale()));
//         }

//         auction.setIncrementValue(newPrice - auction.getMinBid());
//         auctionRepository.save(auction);
//     }

//     private void initializePrice(Auction auction, Float minBid) {
//         auction.setMinBid(minBid);
//         auction.setIncrementValue(0.0F);
//     }

//     public Auction findById(Long id) {
//         return auctionRepository.findById(id)
//             .orElseThrow(() -> new NotFoundException(messageSource.getMessage("exception.auctions.notfound",
//                 new Object[] { id }, LocaleContextHolder.getLocale())));
//     }

//     public Page<Auction> findByAuctioneer(User user, Pageable pageable) {
//         return auctionRepository.findByAuctioneer(user, pageable);
//     }

//     public Page<Auction> findFiltered(AuctionFilterRequest filter, Pageable pageable) {
//         Specification<Auction> spec = AuctionSpecifications.buildFilter(filter);
//         return auctionRepository.findAll(spec, pageable);
//     }

//     private void validatePeriod(LocalDateTime startDateTime, LocalDateTime endDateTime) {
//         if(startDateTime.isAfter(endDateTime)) {
//             throw new BusinessException(messageSource.getMessage("exception.auctions.invalidperiod",
//                 new Object[] {startDateTime, endDateTime}, LocaleContextHolder.getLocale()));
//         }
//     }

//     private void validateOperation(User authUser, Auction auction) {
//         if(!authUser.isSeller() && !authUser.isAdmin()) {
//             throw new ForbiddenException(messageSource.getMessage("exception.auctions.notseller",
//                 new Object[] { authUser.getName() }, LocaleContextHolder.getLocale()));
//         }
//         if(!authUser.isAdmin() && !auction.getAuctioneer().getId().equals(authUser.getId())) {
//             throw new BusinessException(messageSource.getMessage("exception.auctions.notowner",
//                 new Object[] { authUser.getName() }, LocaleContextHolder.getLocale()));
//         }
//     }

//     private void fillComplexAttributes(Auction auction, Category category, User auctioneer) {
//         auction.setCategory(category);
//         auction.setAuctioneer(auctioneer);
//     }

//     private void validateDelete(Auction auction) {
//         if(bidRepository.existsByAuctionAndDeletedFalse(auction)) {
//             throw new BusinessException(messageSource.getMessage("exception.auctions.notDelete",
//                 new Object[] { auction.getTitle() }, LocaleContextHolder.getLocale()));
//         }
//     }
// }
