package com.medicare.service;
import com.medicare.dto.*;
import com.medicare.model.*;
import com.medicare.repository.UserRepository;
import com.medicare.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class AuthService {
 private final UserRepository users; private final PasswordEncoder encoder; private final JwtService jwt;
 public AuthService(UserRepository users,PasswordEncoder encoder,JwtService jwt){this.users=users;this.encoder=encoder;this.jwt=jwt;}
 @Transactional public AuthResponse register(RegisterRequest req){
  if(users.existsByEmailIgnoreCase(req.email())) throw new IllegalArgumentException("An account with this email already exists");
  AppUser user=users.save(new AppUser(req.name().trim(),req.email().trim().toLowerCase(),encoder.encode(req.password()),Role.USER));
  return response(user);
 }
 public AuthResponse login(AuthRequest req){
  AppUser user=users.findByEmailIgnoreCase(req.email()).orElseThrow(()->new IllegalArgumentException("Invalid email or password"));
  if(!encoder.matches(req.password(),user.getPassword())) throw new IllegalArgumentException("Invalid email or password");
  return response(user);
 }
 private AuthResponse response(AppUser user){return new AuthResponse(jwt.generate(user),user.getName(),user.getEmail(),user.getRole().name());}
}
