package com.medicare.service;
import com.medicare.model.Medicine;
import com.medicare.repository.MedicineRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
public class MedicineService {
 private final MedicineRepository repo;
 public MedicineService(MedicineRepository repo){this.repo=repo;}
 public List<Medicine> search(String q,String category,String brand,String symptom){return repo.search(blank(q),blank(category),blank(brand),blank(symptom));}
 public Medicine get(Long id){return repo.findById(id).filter(Medicine::isActive).orElseThrow(()->new IllegalArgumentException("Medicine not found"));}
 @Transactional public Medicine create(Medicine medicine){medicine.setId(null);return repo.save(medicine);}
 @Transactional public Medicine update(Long id,Medicine input){
  Medicine m=repo.findById(id).orElseThrow(()->new IllegalArgumentException("Medicine not found"));
  m.setName(input.getName());m.setDescription(input.getDescription());m.setCategory(input.getCategory());m.setBrand(input.getBrand());m.setSymptoms(input.getSymptoms());m.setPrice(input.getPrice());m.setStock(input.getStock());m.setPrescriptionRequired(input.isPrescriptionRequired());m.setImageUrl(input.getImageUrl());m.setActive(input.isActive());return repo.save(m);
 }
 @Transactional public void delete(Long id){Medicine m=repo.findById(id).orElseThrow(()->new IllegalArgumentException("Medicine not found"));m.setActive(false);repo.save(m);}
 public long count(){return repo.count();}
 private String blank(String s){return s==null||s.isBlank()?null:s.trim();}
}
