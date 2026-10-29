package com.medicare.config;

import com.medicare.model.*;
import com.medicare.repository.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.math.BigDecimal;

@Configuration
public class SeedData {
 @Bean CommandLineRunner seed(UserRepository users, MedicineRepository medicines, PasswordEncoder encoder,
   @Value("${app.demo.admin-email}") String adminEmail,@Value("${app.demo.admin-password}") String adminPassword,
   @Value("${app.demo.user-email}") String userEmail,@Value("${app.demo.user-password}") String userPassword){
  return args -> {
   if(!users.existsByEmailIgnoreCase(adminEmail)) users.save(new AppUser("Demo Administrator",adminEmail,encoder.encode(adminPassword),Role.ADMIN));
   if(!users.existsByEmailIgnoreCase(userEmail)) users.save(new AppUser("Demo Customer",userEmail,encoder.encode(userPassword),Role.USER));
   if(medicines.count()==0){
    medicines.save(med("Vitamin C Tablets","Illustrative wellness product listing.","Vitamins","WellLife","general wellness",149,80,false,"https://images.unsplash.com/photo-1571781926291-c477ebfd024b?w=700"));
    medicines.save(med("Digital Thermometer","Digital thermometer for temperature measurement.","Healthcare Essentials","CarePlus","temperature monitoring",299,35,false,"https://images.unsplash.com/photo-1584634731339-252c581abfc5?w=700"));
    medicines.save(med("First Aid Kit","Basic first aid supplies for home use.","Healthcare Essentials","SafeHome","first aid",399,25,false,"https://images.unsplash.com/photo-1603398938378-e54eab446dde?w=700"));
    medicines.save(med("Daily Multivitamin","Demo listing; consult a qualified professional for health advice.","Vitamins","WellLife","general wellness",249,60,false,"https://images.unsplash.com/photo-1550572017-edd951b55104?w=700"));
   }
  };
 }
 private Medicine med(String n,String d,String c,String b,String s,int p,int stock,boolean rx,String img){
  Medicine m=new Medicine();m.setName(n);m.setDescription(d);m.setCategory(c);m.setBrand(b);m.setSymptoms(s);m.setPrice(BigDecimal.valueOf(p));m.setStock(stock);m.setPrescriptionRequired(rx);m.setImageUrl(img);return m;
 }
}
