package com.example.springbootbookshop.service;

import com.example.springbootbookshop.dto.category.CategoryDto;
import com.example.springbootbookshop.dto.category.CategoryRequestDto;
import com.example.springbootbookshop.entity.Category;
import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAll();

    CategoryDto getById(Long id);

    Category save(CategoryDto categoryDto);

    CategoryDto update(Long id, CategoryRequestDto categoryDto);

    void deleteById(Long id);
}
