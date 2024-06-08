package kz.runtime.springshop.repository;

import kz.runtime.springshop.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

    List<Product> findByPriceBetweenAndCategoryId(Double minPrice, Double maxPrice, Long categoryId);
}