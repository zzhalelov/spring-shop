package kz.runtime.springshop.repository;

import kz.runtime.springshop.model.CartItem;
import kz.runtime.springshop.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findAllByUser(User user);
}