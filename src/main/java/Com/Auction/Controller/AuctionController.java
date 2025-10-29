package Com.Auction.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Com.Auction.DTO.AuctionDTO;
import Com.Auction.Entity.Auction;
import Com.Auction.Entity.Product;
import Com.Auction.Repository.AuctionRepository;
import Com.Auction.Repository.ProductRepository;
import Com.Auction.Service.AuctionService;

import java.util.List;

@RestController
@RequestMapping("/api/auctions")
@CrossOrigin(origins = "*")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private AuctionRepository auctionRepository;

    /**
     * Create an auction for a product
     * @param dto AuctionDTO object containing product ID, start time, and end time
     * @return ResponseEntity with success message
     */
    @PostMapping("/create")
    public ResponseEntity<?> createAuction(@RequestBody AuctionDTO dto) {
        String response = auctionService.createAuction(dto);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all active auctions
     * @return ResponseEntity with a list of active auctions
     */
    @GetMapping("/active")
    public ResponseEntity<List<Auction>> getActiveAuctions() {
        return ResponseEntity.ok(auctionService.getActiveAuctions());
    }

    /**
     * Get auction by product ID
     * @param productId ID of the product to search the auction for
     * @return ResponseEntity with the auction for the given product
     */
    @GetMapping("/product/{productId}")
    public ResponseEntity<?> getAuctionByProduct(@PathVariable Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

        Auction auction = auctionRepository.findByProduct(product);
        if (auction != null) {
            return ResponseEntity.ok(auction);
        } else {
            return ResponseEntity.status(404).body("Auction not found for this product.");
        }
    }
}
