package com.devsk.blog.services;

import java.util.List;

import com.devsk.blog.domain.entities.Category;

public interface CategoryService {

    List<Category> listCategories();
    Category createCategory(Category category);
    void deleteCategory(Long id);
    Category getCategoryById(Long id);
}
