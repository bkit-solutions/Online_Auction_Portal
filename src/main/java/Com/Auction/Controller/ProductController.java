package Com.Auction.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import Com.Auction.Entity.Product;
import Com.Auction.Repository.ProductRepository;
import Com.Auction.Service.ProductService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;
    
    @Autowired
    private ProductRepository productRepository;
    

    @PostMapping("/upload")
    public ResponseEntity<String> uploadProduct(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("basePrice") Double basePrice,
            @RequestParam("sellerId") Long sellerId,
            @RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file selected");
        }

        try {
            // Call the service to upload product with metadata
            String response = productService.uploadProduct(name, description, basePrice, file, sellerId);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Failed to upload file: " + e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/")
    public List<Product> getAllProducts() {
        return productRepository.findAll(); // This returns all products
    }
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<Product>> getProductsBySeller(@PathVariable Long sellerId) {
        List<Product> products = productRepository.findBySellerId(sellerId);
        return ResponseEntity.ok(products);
    }
}
