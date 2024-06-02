package kz.runtime.springshop.service.impl;

import jakarta.persistence.EntityNotFoundException;
import kz.runtime.springshop.model.Category;
import kz.runtime.springshop.model.Option;
import kz.runtime.springshop.model.Product;
import kz.runtime.springshop.model.Value;
import kz.runtime.springshop.repository.CategoryRepository;
import kz.runtime.springshop.repository.OptionRepository;
import kz.runtime.springshop.repository.ProductRepository;
import kz.runtime.springshop.repository.ValueRepository;
import kz.runtime.springshop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final OptionRepository optionRepository;
    private final ValueRepository valueRepository;

    @Override
    public void create(Product product, long categoryId, List<Long> optionIds, List<String> values) {
        // Находим категорию по id
        Category category = categoryRepository.findById(categoryId).orElseThrow();

        // Устанавливаем категорию для товара
        product.setCategory(category);

        // Сохраняем товар в БД
        productRepository.save(product);

        // Выгружаем все Option объекты разом
        List<Option> options = optionRepository.findAllById(optionIds);

        for (int i = 0; i < optionIds.size(); i++) {
            // Достаем Option из списка
            Option option = options.get(i);

            // Достаем значение характеристики
            String valueName = values.get(i);

            // Создание объекта Value (значение характеристики)
            Value value = new Value();
            value.setName(valueName);
            value.setProduct(product);
            value.setOption(option);
            valueRepository.save(value);
        }
    }

    @Override
    public void update(long productId, String updatedName, double updatedPrice, List<Long> optionIds, List<String> values) {
        Product product = productRepository.findById(productId).orElseThrow();
        product.setPrice(updatedPrice);
        product.setName(updatedName);

        productRepository.save(product);

        for (int i = 0; i < optionIds.size(); i++) {
            long optionId = optionIds.get(i);
            Option option = optionRepository.findById(optionId).orElseThrow();
            Value value = valueRepository.findByProductAndOption(product, option)
                    .orElseGet(() -> {
                        Value newValue = new Value();
                        newValue.setProduct(product);
                        newValue.setOption(option);
                        return newValue;
                    });
            value.setName(values.get(i));
            valueRepository.save(value);
        }
    }

    @Override
    public Map<Option, Optional<Value>> getOptions(Product product) {
        Map<Option, Optional<Value>> result = new LinkedHashMap<>();

        long categoryId = product.getCategory().getId();
        List<Option> options = optionRepository.findAllByCategoryId(categoryId);
        for (Option option : options) {
            Optional<Value> optionalValue = valueRepository.findByProductAndOption(product, option);
            result.put(option, optionalValue);
        }

        return result;
    }

    @Override
    public List<Product> findAll(Long categoryId, int from, int to) {
        return productRepository.findAllByPriceBetween(categoryId, from, to);
    }

    @Override
    public Product findById(long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Товар с id=" + id + " не найден!"));
    }

    @Override
    public void deleteById(long id) {
        valueRepository.deleteAllByProductId(id);
        productRepository.deleteById(id);
    }
}