package com.medicare.controller;
import com.medicare.dto.OrderRequest;
import com.medicare.dto.StatusRequest;
import com.medicare.model.CustomerOrder;
import com.medicare.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/orders")
public class OrderController {
 private final OrderService service;
 public OrderController(OrderService service){this.service=service;}
 @PostMapping public CustomerOrder place(Authentication auth,@Valid @RequestBody OrderRequest request){return service.place(auth.getName(),request);}
 @GetMapping("/my") public List<CustomerOrder> mine(Authentication auth){return service.myOrders(auth.getName());}
 @GetMapping public List<CustomerOrder> all(){return service.all();}
 @PatchMapping("/{id}/status") public CustomerOrder status(@PathVariable Long id,@Valid @RequestBody StatusRequest request){return service.updateStatus(id,request.status());}
}
