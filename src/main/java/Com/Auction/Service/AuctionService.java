package Com.Auction.Service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import Com.Auction.DTO.AuctionDTO;
import Com.Auction.Entity.Auction;
import Com.Auction.Entity.Product;
import Com.Auction.Entity.User;
import Com.Auction.Repository.AuctionRepository;
import Com.Auction.Repository.ProductRepository;
import Com.Auction.Repository.UserRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Creates an auction and sends email notifications to all buyers
     * @param dto AuctionDTO object containing auction details
     * @return Success message
     */
    @Transactional
    public String createAuction(AuctionDTO dto) {
        // Validate the product ID
        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product with ID " + dto.getProductId() + " not found"));

        // Parse start and end time
        LocalDateTime start;
        LocalDateTime end;
        try {
            start = LocalDateTime.parse(dto.getStartTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            end = LocalDateTime.parse(dto.getEndTime(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            System.out.println("the start time is"+start);
            System.out.println("the end time time is"+end);
        } catch (Exception e) {
            throw new RuntimeException("Invalid date format. Please provide a valid date in ISO format (yyyy-MM-dd'T'HH:mm).");
        }

        // Validate that end time is after start time
        if (end.isBefore(start)) {
            throw new RuntimeException("End time cannot be before start time.");
        }

        // Create and save auction
        Auction auction = new Auction(product, start, end);
        auctionRepository.save(auction);

        // Send email to all buyers
        List<User> buyers = userRepository.findAll().stream()
                .filter(user -> user.getRole() == User.Role.BUYER)
                .collect(Collectors.toList());

        for (User buyer : buyers) {
            sendEmail(buyer.getEmail(), product.getName(), start, end);
        }

        return "Auction created successfully and notifications sent to buyers.";
    }

    /**
     * Sends email notification to buyers about new auction
     * @param to The email of the recipient
     * @param productName The product being auctioned
     * @param start The start time of the auction
     * @param end The end time of the auction
     */
    private void sendEmail(String to, String productName, LocalDateTime start, LocalDateTime end) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("New Auction Scheduled");

        message.setText("An auction for product \"" + productName + "\" has been scheduled.\n\n" +
                "Start: " + start.toString() + "\n" +
                "End: " + end.toString() + "\n\n" +
                "Login to the portal to participate.");

        try {
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println("Failed to send email to " + to + ": " + e.getMessage());
        }
    }

    /**
     * Retrieves all active auctions that are ongoing
     * @return List of active auctions
     */
    public List<Auction> getActiveAuctions() {
        LocalDateTime now = LocalDateTime.now();
        return auctionRepository.findByStartTimeBeforeAndEndTimeAfter(now, now);
    }
}
