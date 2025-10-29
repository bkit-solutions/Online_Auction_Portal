package Com.Auction.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import Com.Auction.Entity.Product;
import Com.Auction.Entity.User;
import Com.Auction.Repository.ProductRepository;
import Com.Auction.Repository.UserRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class ProductService {

    // Recommended: absolute upload directory
	private static final String UPLOAD_DIR = System.getProperty("user.dir") + "/uploads";


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Upload a product (image + metadata)
     */
    public String uploadProduct(String name, String description, Double basePrice,
                                MultipartFile image, Long sellerId) throws IOException {

        // ✅ Validate seller
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found with ID: " + sellerId));

        // ✅ Create upload directory if not exists
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            boolean created = uploadDir.mkdirs();
            if (!created) {
                throw new IOException("Failed to create upload directory: " + UPLOAD_DIR);
            }
        }

        // ✅ Sanitize and resolve image file name
        String safeFileName = Paths.get(image.getOriginalFilename()).getFileName().toString();
        Path imagePath = Paths.get(UPLOAD_DIR, safeFileName);
        File destFile = imagePath.toFile();

        // ✅ Debug log
        System.out.println("Saving uploaded file to: " + destFile.getAbsolutePath());

        // ✅ Save file to disk
        try {
            image.transferTo(destFile);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new IOException("Failed to save file: " + ex.getMessage(), ex);
        }

        // ✅ Save product to DB
        Product product = new Product(name, description, basePrice, safeFileName, seller);
        productRepository.save(product);

        return "Product uploaded successfully";
    }

    /**
     * Get products by seller
     */
    public List<Product> getSellerProducts(Long sellerId) {
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found with ID: " + sellerId));
        return productRepository.findBySeller(seller);
    }
}
