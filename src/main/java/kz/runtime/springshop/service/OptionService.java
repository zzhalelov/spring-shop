package kz.runtime.springshop.service;

import kz.runtime.springshop.model.Category;
import kz.runtime.springshop.model.Option;

import java.util.List;

public interface OptionService extends AbstractService<Option> {
    void create(String optionNames, Category category);

    List<Option> findAllByCategoryId(long categoryId);
}