package com.medicare.security;

import com.medicare.model.AppUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtService {
 @Value("${app.jwt.secret}") private String secret;
 @Value("${app.jwt.expiration-ms}") private long expirationMs;
 private SecretKey key(){return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));}
 public String generate(AppUser user){
   return Jwts.builder().subject(user.getEmail()).claim("role",user.getRole().name())
     .issuedAt(new Date()).expiration(new Date(System.currentTimeMillis()+expirationMs))
     .signWith(key()).compact();
 }
 public String extractEmail(String token){return Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload().getSubject();}
 public boolean isValid(String token){try{return Jwts.parser().verifyWith(key()).build().parseSignedClaims(token).getPayload().getExpiration().after(new Date());}catch(Exception ex){return false;}}
}
