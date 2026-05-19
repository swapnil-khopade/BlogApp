package com.devsk.blog.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsk.blog.domain.dtos.CategoryDto;
import com.devsk.blog.domain.dtos.CreateCategoryRequest;
import com.devsk.blog.domain.entities.Category;
import com.devsk.blog.mappers.CategoryMapper;
import com.devsk.blog.services.CategoryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping(path = "/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    @GetMapping
    public ResponseEntity<List<CategoryDto>> listCategories(){
        
        List<CategoryDto> categories =  categoryService.listCategories()
            .stream().map(categoryMapper::toDto)
            .toList();
        
        return ResponseEntity.ok(categories);
    }

    @PostMapping
    public ResponseEntity<CategoryDto> createCategories(
        @Valid @RequestBody CreateCategoryRequest createCategoryRequest){

            Category categoryToCreate = categoryMapper.toEntity(createCategoryRequest);
            Category saveCategory = categoryService.createCategory(categoryToCreate);

            return new ResponseEntity<>(
                categoryMapper.toDto(saveCategory),
                HttpStatus.CREATED
            );
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
        categoryService.deleteCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
