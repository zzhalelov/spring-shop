package kz.runtime.springshop.repository;

import kz.runtime.springshop.model.Product;
import kz.runtime.springshop.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByProduct(Product product);
}