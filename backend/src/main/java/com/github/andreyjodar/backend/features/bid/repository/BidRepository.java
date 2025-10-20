// package com.github.andreyjodar.backend.features.bid.repository;

// import java.util.Optional;

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

// import com.github.andreyjodar.backend.features.auction.model.Auction;
// import com.github.andreyjodar.backend.features.bid.model.Bid;
// import com.github.andreyjodar.backend.features.user.model.User;

// public interface BidRepository extends JpaRepository<Bid, Long>, JpaSpecificationExecutor<Bid> {
//     Page<Bid> findByBidder(User bidder, Pageable pageable);

//     Optional<Bid> findTopByAuctionIdOrderByBidValueDesc(Long auctionId); 
//     boolean existsByAuctionAndDeletedFalse(Auction auction);
// }
