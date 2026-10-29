package com.medicare.service;

import com.medicare.model.Medicine;
import com.medicare.repository.MedicineRepository;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MedicineServiceTest {
 @Test void searchDelegatesFiltersToRepository(){
  MedicineRepository repo=mock(MedicineRepository.class);
  MedicineService service=new MedicineService(repo);
  when(repo.search("vitamin",null,null,null)).thenReturn(List.of(new Medicine()));
  assertEquals(1,service.search(" vitamin ",null,null,null).size());
  verify(repo).search("vitamin",null,null,null);
 }
 @Test void missingMedicineThrows(){
  MedicineRepository repo=mock(MedicineRepository.class);
  when(repo.findById(999L)).thenReturn(java.util.Optional.empty());
  assertThrows(IllegalArgumentException.class,()->new MedicineService(repo).get(999L));
 }
}
