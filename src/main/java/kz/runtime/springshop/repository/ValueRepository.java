package kz.runtime.springshop.repository;

import kz.runtime.springshop.model.Option;
import kz.runtime.springshop.model.Product;
import kz.runtime.springshop.model.Value;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface ValueRepository extends JpaRepository<Value, Long> {
    @Transactional
    void deleteAllByProductId(long productId);

    Optional<Value> findByProductAndOption(Product product, Option option);
}