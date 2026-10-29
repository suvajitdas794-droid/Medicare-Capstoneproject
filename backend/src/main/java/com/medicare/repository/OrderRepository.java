package com.medicare.repository;
import com.medicare.model.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
public interface OrderRepository extends JpaRepository<CustomerOrder,Long> {
 List<CustomerOrder> findByUserIdOrderByCreatedAtDesc(Long userId);
 List<CustomerOrder> findAllByOrderByCreatedAtDesc();
 long countByStatus(String status);
}
