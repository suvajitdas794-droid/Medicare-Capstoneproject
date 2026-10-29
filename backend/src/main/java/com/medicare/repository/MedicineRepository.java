package com.medicare.repository;
import com.medicare.model.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
public interface MedicineRepository extends JpaRepository<Medicine,Long> {
 @Query("select m from Medicine m where m.active=true and (:q is null or lower(m.name) like lower(concat('%',:q,'%')) or lower(coalesce(m.description,'')) like lower(concat('%',:q,'%'))) and (:category is null or lower(m.category)=lower(:category)) and (:brand is null or lower(coalesce(m.brand,''))=lower(:brand)) and (:symptom is null or lower(coalesce(m.symptoms,'')) like lower(concat('%',:symptom,'%')))")
 List<Medicine> search(@Param("q") String q,@Param("category") String category,@Param("brand") String brand,@Param("symptom") String symptom);
}
