package com.medicare.service;
import com.medicare.dto.OrderRequest;
import com.medicare.model.*;
import com.medicare.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
@Service
public class OrderService {
 private final OrderRepository orders; private final UserRepository users; private final MedicineRepository medicines;
 public OrderService(OrderRepository orders,UserRepository users,MedicineRepository medicines){this.orders=orders;this.users=users;this.medicines=medicines;}
 @Transactional public CustomerOrder place(String email,OrderRequest request){
  AppUser user=users.findByEmailIgnoreCase(email).orElseThrow(()->new IllegalArgumentException("User not found"));
  CustomerOrder order=new CustomerOrder();order.setUser(user);order.setShippingAddress(request.shippingAddress().trim());order.setStatus(OrderStatus.PLACED);
  BigDecimal total=BigDecimal.ZERO;
  for(OrderRequest.Item item:request.items()){
   Medicine med=medicines.findById(item.medicineId()).filter(Medicine::isActive).orElseThrow(()->new IllegalArgumentException("Medicine not found: "+item.medicineId()));
   if(item.quantity()<1)throw new IllegalArgumentException("Quantity must be at least one");
   if(med.getStock()<item.quantity())throw new IllegalArgumentException("Insufficient stock for "+med.getName());
   med.setStock(med.getStock()-item.quantity());
   order.addItem(new OrderItem(med,item.quantity()));
   total=total.add(med.getPrice().multiply(BigDecimal.valueOf(item.quantity())));
  }
  order.setTotal(total);return orders.save(order);
 }
 public List<CustomerOrder> myOrders(String email){AppUser user=users.findByEmailIgnoreCase(email).orElseThrow(()->new IllegalArgumentException("User not found"));return orders.findByUserIdOrderByCreatedAtDesc(user.getId());}
 public List<CustomerOrder> all(){return orders.findAllByOrderByCreatedAtDesc();}
 @Transactional public CustomerOrder updateStatus(Long id,OrderStatus status){
  CustomerOrder order=orders.findById(id).orElseThrow(()->new IllegalArgumentException("Order not found"));
  if(order.getStatus()==OrderStatus.DELIVERED || order.getStatus()==OrderStatus.CANCELLED)throw new IllegalArgumentException("A completed order cannot be changed");
  order.setStatus(status);return orders.save(order);
 }
 public long count(){return orders.count();}
}
