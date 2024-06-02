package kz.runtime.springshop.service.impl;

import kz.runtime.springshop.model.Category;
import kz.runtime.springshop.model.Option;
import kz.runtime.springshop.repository.CategoryRepository;
import kz.runtime.springshop.repository.OptionRepository;
import kz.runtime.springshop.service.OptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OptionServiceImpl implements OptionService {

    private final OptionRepository optionRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<Option> findAll() {
        return optionRepository.findAll();
    }

    @Override
    public List<Option> findAllByCategoryId(long categoryId) {
        return optionRepository.findAllByCategoryId(categoryId);
    }

    @Override
    public Option findById(long id) {
        return optionRepository.findById(id).orElseThrow();
    }

    @Override
    public void create(String optionNames, Category category) {
        for (String optionName : optionNames.split(", ")) {
            Option option = new Option();
            option.setName(optionName);
            option.setCategory(category);
            optionRepository.save(option);
            System.out.printf("Создана характеристика {%s}\n", optionName);
        }
    }

    @Override
    public void update(long id, Option updatedEntity) {
        // TODO
    }

    @Override
    public void deleteById(long id) {
        // TODO
    }

    // NOT IMPLEMENTED
    @Override
    public void create(Option entity) {
        throw new IllegalArgumentException();
    }
}