package Com.Auction.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import Com.Auction.Entity.Auction;
import Com.Auction.Entity.Product;

import java.time.LocalDateTime;
import java.util.List;

public interface AuctionRepository extends JpaRepository<Auction, Long> {
	
    Auction findByProduct(Product product);

    List<Auction> findByStartTimeBeforeAndEndTimeAfter(LocalDateTime now1, LocalDateTime now2); // Active auctions
}
