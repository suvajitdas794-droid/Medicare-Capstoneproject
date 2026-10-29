package com.medicare.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;

@Entity
@Table(name="order_items")
public class OrderItem {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @JsonIgnore @ManyToOne(fetch=FetchType.EAGER, optional=false) @JoinColumn(name="order_id") private CustomerOrder order;
    @ManyToOne(fetch=FetchType.EAGER, optional=false) @JoinColumn(name="medicine_id") private Medicine medicine;
    @Column(nullable=false) private String medicineName;
    @Column(nullable=false) private int quantity;
    @Column(nullable=false, precision=10, scale=2) private BigDecimal unitPrice;
    public OrderItem(){}
    public OrderItem(Medicine medicine,int quantity){this.medicine=medicine;this.medicineName=medicine.getName();this.quantity=quantity;this.unitPrice=medicine.getPrice();}
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public CustomerOrder getOrder(){return order;} public void setOrder(CustomerOrder order){this.order=order;}
    public Medicine getMedicine(){return medicine;} public void setMedicine(Medicine medicine){this.medicine=medicine;}
    public String getMedicineName(){return medicineName;} public void setMedicineName(String name){this.medicineName=name;}
    public int getQuantity(){return quantity;} public void setQuantity(int quantity){this.quantity=quantity;}
    public BigDecimal getUnitPrice(){return unitPrice;} public void setUnitPrice(BigDecimal price){this.unitPrice=price;}
}
