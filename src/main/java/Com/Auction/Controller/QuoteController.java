package Com.Auction.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import Com.Auction.DTO.QuoteDTO;
import Com.Auction.Entity.Quote;
import Com.Auction.Service.QuoteService;

import java.util.List;

@RestController
@RequestMapping("/api/quotes")
@CrossOrigin(origins = "*")
public class QuoteController {

	@Autowired
	private QuoteService quoteService;

	// Submit a quote (buyer-side)
	@PostMapping("/submit")
	public ResponseEntity<String> submitQuote(@RequestBody QuoteDTO dto) {
	    try {
	        quoteService.submitQuote(dto);
	        return ResponseEntity.ok("Quote submitted successfully.");
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body("Error submitting quote: " + e.getMessage());
	    }
	}

	// Get all quotes for a specific auction (seller-side)
	@GetMapping("/auction/{auctionId}")
	public ResponseEntity<List<Quote>> getQuotesByAuction(@PathVariable Long auctionId) {
	    List<Quote> quotes = quoteService.getQuotesByAuction(auctionId);
	    return ResponseEntity.ok(quotes);
	}

	// Approve the highest quote for an auction
	@PostMapping("/approve/{auctionId}")
	public ResponseEntity<String> approveTopQuote(@PathVariable Long auctionId) {
	    try {
	        String result = quoteService.approveHighestQuote(auctionId);
	        return ResponseEntity.ok(result);
	    } catch (Exception e) {
	        return ResponseEntity.badRequest().body("Error approving quote: " + e.getMessage());
	    }
	}
}
