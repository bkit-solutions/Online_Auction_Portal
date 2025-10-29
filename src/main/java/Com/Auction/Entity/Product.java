package Com.Auction.Entity;


import jakarta.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 1000)
    private String description;

    private Double basePrice;

    private String imagePath; // path or filename of uploaded image

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    public Product() {}

    public Product(String name, String description, Double basePrice, String imagePath, User seller) {
        this.name = name;
        this.description = description;
        this.basePrice = basePrice;
        this.imagePath = imagePath;
        this.seller = seller;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getBasePrice() { return basePrice; }
    public void setBasePrice(Double basePrice) { this.basePrice = basePrice; }

    public String getImagePath() { return imagePath; }
    public void setImagePath(String imagePath) { this.imagePath = imagePath; }

    public User getSeller() { return seller; }
    public void setSeller(User seller) { this.seller = seller; }
}
