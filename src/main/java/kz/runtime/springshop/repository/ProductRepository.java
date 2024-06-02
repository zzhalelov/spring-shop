package kz.runtime.springshop.repository;

import kz.runtime.springshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p " +
            "FROM Product p " +
            "WHERE (:categoryId IS NULL OR p.category.id = :categoryId) " +
            "  AND p.price BETWEEN :from AND :to")
    List<Product> findAllByPriceBetween(Long categoryId, double from, double to);
}