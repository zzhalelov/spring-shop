package kz.runtime.springshop.service;

import kz.runtime.springshop.model.Option;
import kz.runtime.springshop.model.Product;
import kz.runtime.springshop.model.Value;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface ProductService {
    void create(Product product, long categoryId, List<Long> optionIds, List<String> values);

    void update(long productId, String updatedName, double updatedPrice, List<Long> optionIds, List<String> values);

    List<Product> findAll(Long categoryId, int from, int to);

    Product findById(long id);

    void deleteById(long id);

    Map<Option, Optional<Value>> getOptions(Product product);

    List<Product> findByFilters(Double minPrice, Double maxPrice, Long categoryId);
}