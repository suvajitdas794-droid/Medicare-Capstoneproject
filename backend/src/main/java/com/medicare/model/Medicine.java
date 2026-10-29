package com.medicare.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name="medicines")
public class Medicine {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @NotBlank @Column(nullable=false) private String name;
    @Column(length=2000) private String description;
    @NotBlank @Column(nullable=false) private String category;
    private String brand;
    @Column(length=500) private String symptoms;
    @NotNull @DecimalMin("0.01") @Column(nullable=false, precision=10, scale=2) private BigDecimal price;
    @Min(0) @Column(nullable=false) private int stock;
    @Column(nullable=false) private boolean prescriptionRequired;
    @Column(length=500) private String imageUrl;
    @Column(nullable=false) private boolean active=true;
    private LocalDateTime createdAt=LocalDateTime.now();

    public Medicine() {}
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getDescription(){return description;} public void setDescription(String description){this.description=description;}
    public String getCategory(){return category;} public void setCategory(String category){this.category=category;}
    public String getBrand(){return brand;} public void setBrand(String brand){this.brand=brand;}
    public String getSymptoms(){return symptoms;} public void setSymptoms(String symptoms){this.symptoms=symptoms;}
    public BigDecimal getPrice(){return price;} public void setPrice(BigDecimal price){this.price=price;}
    public int getStock(){return stock;} public void setStock(int stock){this.stock=stock;}
    public boolean isPrescriptionRequired(){return prescriptionRequired;} public void setPrescriptionRequired(boolean value){this.prescriptionRequired=value;}
    public String getImageUrl(){return imageUrl;} public void setImageUrl(String imageUrl){this.imageUrl=imageUrl;}
    public boolean isActive(){return active;} public void setActive(boolean active){this.active=active;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime createdAt){this.createdAt=createdAt;}
}
