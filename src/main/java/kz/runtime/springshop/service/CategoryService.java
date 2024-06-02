package kz.runtime.springshop.service;

import kz.runtime.springshop.model.Category;
import kz.runtime.springshop.model.Option;

import java.util.List;

public interface CategoryService extends AbstractService<Category> {
    List<Option> findOptionsByCategoryId(long categoryId);
}