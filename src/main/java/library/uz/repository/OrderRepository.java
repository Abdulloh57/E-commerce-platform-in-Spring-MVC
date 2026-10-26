package library.uz.repository;

import library.uz.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByUseridOrderByIdDesc(int userid);
}
