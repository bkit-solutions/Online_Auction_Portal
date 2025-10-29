package Com.Auction.DTO;


public class AuctionDTO {
    private Long productId;
    private String startTime; // as ISO string
    private String endTime;

    public AuctionDTO() {}

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }

    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
}
