package Com.Auction.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import Com.Auction.Entity.Auction;
import Com.Auction.Entity.Quote;
import Com.Auction.Entity.User;

import java.util.List;
import java.util.Optional;

public interface QuoteRepository extends JpaRepository<Quote, Long> {
    List<Quote> findByAuction(Auction auction);
    List<Quote> findByBuyer(User buyer);
    Optional<Quote> findTopByAuctionOrderByAmountDesc(Auction auction);
}
