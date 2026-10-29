package com.medicare.controller;
import com.medicare.repository.UserRepository;
import com.medicare.service.MedicineService;
import com.medicare.service.OrderService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController @RequestMapping("/api/admin")
public class AdminController {
 private final UserRepository users; private final MedicineService medicines; private final OrderService orders;
 public AdminController(UserRepository users,MedicineService medicines,OrderService orders){this.users=users;this.medicines=medicines;this.orders=orders;}
 @GetMapping("/dashboard") public Map<String,Long> dashboard(){return Map.of("users",users.count(),"medicines",medicines.count(),"orders",orders.count());}
 @GetMapping("/users") public Object users(){return users.findAll().stream().map(u->Map.of("id",u.getId(),"name",u.getName(),"email",u.getEmail(),"role",u.getRole().name(),"createdAt",u.getCreatedAt())).toList();}
}
