package com.example.springbootbookshop.service;

import com.example.springbootbookshop.dto.category.CategoryDto;
import com.example.springbootbookshop.dto.category.CategoryRequestDto;
import java.util.List;

public interface CategoryService {
    List<CategoryDto> findAll();

    CategoryDto getById(Long id);

    CategoryDto save(CategoryDto categoryDto);

    CategoryDto update(Long id, CategoryRequestDto categoryDto);

    void deleteById(Long id);
}
