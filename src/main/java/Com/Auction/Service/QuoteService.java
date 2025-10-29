package Com.Auction.Service;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import Com.Auction.DTO.QuoteDTO;
import Com.Auction.Entity.Auction;
import Com.Auction.Entity.Quote;
import Com.Auction.Entity.User;
import Com.Auction.Repository.AuctionRepository;
import Com.Auction.Repository.QuoteRepository;
import Com.Auction.Repository.UserRepository;

import java.util.List;

@Service
public class QuoteService {

    @Autowired
    private QuoteRepository quoteRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;

    public String submitQuote(QuoteDTO dto) {
        Auction auction = auctionRepository.findById(dto.getAuctionId())
                .orElseThrow(() -> new RuntimeException("Auction not found"));

        User buyer = userRepository.findById(dto.getBuyerId())
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        Quote quote = new Quote(dto.getAmount(), auction, buyer);
        quoteRepository.save(quote);

        return "Quote submitted successfully";
    }

    public List<Quote> getQuotesByAuction(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new RuntimeException("Auction not found"));
        return quoteRepository.findByAuction(auction);
    }

    @Transactional
    public String approveHighestQuote(Long auctionId) {
        Auction auction = auctionRepository.findById(auctionId)
                .orElseThrow(() -> new RuntimeException("Auction not found"));

        Quote topQuote = quoteRepository.findTopByAuctionOrderByAmountDesc(auction)
                .orElseThrow(() -> new RuntimeException("No quotes available"));

        topQuote.setApproved(true);
        quoteRepository.save(topQuote);

        sendApprovalEmail(topQuote);

        return "Top quote approved and email sent to buyer";
    }

    private void sendApprovalEmail(Quote quote) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(quote.getBuyer().getEmail());
        message.setSubject("Quote Approved");
        message.setText("Congratulations! Your quote of ₹" + quote.getAmount() +
                " for the auctioned product has been approved by the seller.");
        mailSender.send(message);
    }
}
