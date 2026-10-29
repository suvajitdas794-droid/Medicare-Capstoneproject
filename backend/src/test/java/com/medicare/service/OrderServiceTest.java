package com.medicare.service;

import com.medicare.dto.OrderRequest;
import com.medicare.model.*;
import com.medicare.repository.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {
 @Test void rejectsInsufficientStock(){
  OrderRepository orders=mock(OrderRepository.class);UserRepository users=mock(UserRepository.class);MedicineRepository meds=mock(MedicineRepository.class);
  AppUser user=new AppUser("A","a@b.com","hash",Role.USER);user.setId(1L);
  Medicine med=new Medicine();med.setId(3L);med.setName("Test");med.setPrice(BigDecimal.TEN);med.setStock(1);med.setActive(true);
  when(users.findByEmailIgnoreCase("a@b.com")).thenReturn(Optional.of(user));when(meds.findById(3L)).thenReturn(Optional.of(med));
  OrderService service=new OrderService(orders,users,meds);
  assertThrows(IllegalArgumentException.class,()->service.place("a@b.com",new OrderRequest("Address",List.of(new OrderRequest.Item(3L,2)))));
  verify(orders,never()).save(any());
 }
}
