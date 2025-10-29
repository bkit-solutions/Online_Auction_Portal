package Com.Auction.Repository;


import org.springframework.data.jpa.repository.JpaRepository;

import Com.Auction.Entity.Product;
import Com.Auction.Entity.User;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findBySeller(User seller);

	List<Product> findBySellerId(Long sellerId);
}
