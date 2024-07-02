package kz.runtime.springshop.repository;

import kz.runtime.springshop.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}