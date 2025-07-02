package com.jocata.productservice.entity;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_details")

public class ProductDetails {
    @Id
    @Column(name = "product_id")
    private Integer id;
    @Column(name="weight")

    private BigDecimal weight;
    @Column(name="dimensions")
    private String dimensions;
    @Column(name="color")

    private String color;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToOne
    @MapsId
    @JoinColumn(name = "product_id")
    private Product product;


    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public BigDecimal getWeight() { return weight; }
    public void setWeight(BigDecimal weight) { this.weight = weight; }

    public String getDimensions() { return dimensions; }
    public void setDimensions(String dimensions) { this.dimensions = dimensions; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }
}
