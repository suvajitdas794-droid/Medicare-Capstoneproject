package com.medicare.controller;
import com.medicare.model.Medicine;
import com.medicare.service.MedicineService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/medicines")
public class MedicineController {
 private final MedicineService service;
 public MedicineController(MedicineService service){this.service=service;}
 @GetMapping public List<Medicine> search(@RequestParam(required=false) String q,@RequestParam(required=false) String category,@RequestParam(required=false) String brand,@RequestParam(required=false) String symptom){return service.search(q,category,brand,symptom);}
 @GetMapping("/{id}") public Medicine get(@PathVariable Long id){return service.get(id);}
 @PostMapping public Medicine create(@Valid @RequestBody Medicine m){return service.create(m);}
 @PutMapping("/{id}") public Medicine update(@PathVariable Long id,@Valid @RequestBody Medicine m){return service.update(id,m);}
 @DeleteMapping("/{id}") public void delete(@PathVariable Long id){service.delete(id);}
}
