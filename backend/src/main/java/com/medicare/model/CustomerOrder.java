package com.medicare.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="customer_orders")
public class CustomerOrder {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @ManyToOne(fetch=FetchType.EAGER, optional=false) @JoinColumn(name="user_id") private AppUser user;
    @Enumerated(EnumType.STRING) @Column(nullable=false) private OrderStatus status=OrderStatus.PLACED;
    @Column(nullable=false, precision=10, scale=2) private BigDecimal total;
    @Column(nullable=false, length=500) private String shippingAddress;
    private LocalDateTime createdAt=LocalDateTime.now();
    @OneToMany(mappedBy="order", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
    private List<OrderItem> items=new ArrayList<>();
    public CustomerOrder(){}
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public AppUser getUser(){return user;} public void setUser(AppUser user){this.user=user;}
    public OrderStatus getStatus(){return status;} public void setStatus(OrderStatus status){this.status=status;}
    public BigDecimal getTotal(){return total;} public void setTotal(BigDecimal total){this.total=total;}
    public String getShippingAddress(){return shippingAddress;} public void setShippingAddress(String address){this.shippingAddress=address;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime createdAt){this.createdAt=createdAt;}
    public List<OrderItem> getItems(){return items;} public void setItems(List<OrderItem> items){this.items=items;}
    public void addItem(OrderItem item){items.add(item); item.setOrder(this);}
}
