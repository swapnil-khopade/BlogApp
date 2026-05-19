package com.devsk.blog.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.devsk.blog.domain.entities.Category;
import com.devsk.blog.repositories.CategoryRepository;
import com.devsk.blog.services.CategoryService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public List<Category> listCategories() {
        return categoryRepository.findAllWithPostCount();
        
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        String categoryName = category.getName();
        if(categoryRepository.existsByNameIgnoreCase(categoryName)){
            throw new IllegalArgumentException("Category already exists with name: "+categoryName);
        }
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long id) {
        Optional<Category> category =  categoryRepository.findById(id);
        if(category.isPresent()){
            if (category.get().getPosts().size()>0) {
                throw new IllegalStateException("Category has post associated with it");
            }
            categoryRepository.deleteById(id);
        }
        
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Categorry not found with id "+id));
    }

}
