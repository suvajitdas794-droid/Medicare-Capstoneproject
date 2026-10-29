package com.medicare.config;

import com.medicare.security.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.Arrays;
import java.util.List;

@Configuration
public class SecurityConfig {
 private final JwtAuthFilter jwtFilter;
 @Value("${app.cors.allowed-origins}") private String allowedOrigins;
 public SecurityConfig(JwtAuthFilter jwtFilter){this.jwtFilter=jwtFilter;}
 @Bean PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();}
 @Bean SecurityFilterChain filterChain(HttpSecurity http)throws Exception{
   return http.csrf(csrf->csrf.disable()).cors(Customizer.withDefaults())
    .sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    .authorizeHttpRequests(auth->auth
      .requestMatchers("/api/auth/**","/swagger-ui/**","/v3/api-docs/**","/actuator/health","/error").permitAll()
      .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/medicines", "/api/medicines/**").permitAll()
      .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/medicines/**").hasRole("ADMIN")
      .requestMatchers(org.springframework.http.HttpMethod.PUT, "/api/medicines/**").hasRole("ADMIN")
      .requestMatchers(org.springframework.http.HttpMethod.DELETE, "/api/medicines/**").hasRole("ADMIN")
      .requestMatchers("/api/admin/**").hasRole("ADMIN")
      .requestMatchers(org.springframework.http.HttpMethod.POST, "/api/orders").hasRole("USER")
      .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/orders/my").hasRole("USER")
      .requestMatchers(org.springframework.http.HttpMethod.GET, "/api/orders").hasRole("ADMIN")
      .requestMatchers(org.springframework.http.HttpMethod.PATCH, "/api/orders/**").hasRole("ADMIN")
      .anyRequest().authenticated())
    .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class).build();
 }
 @Bean CorsConfigurationSource corsConfigurationSource(){
   CorsConfiguration config=new CorsConfiguration();
   config.setAllowedOrigins(Arrays.stream(allowedOrigins.split(",")).map(String::trim).toList());
   config.setAllowedMethods(List.of("GET","POST","PUT","PATCH","DELETE","OPTIONS"));
   config.setAllowedHeaders(List.of("Authorization","Content-Type","Accept"));
   config.setExposedHeaders(List.of("Authorization"));
   UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();source.registerCorsConfiguration("/**",config);return source;
 }
}
