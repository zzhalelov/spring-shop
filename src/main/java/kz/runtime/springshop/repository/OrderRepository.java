package kz.runtime.springshop.repository;

import kz.runtime.springshop.model.Order;
import kz.runtime.springshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);
}