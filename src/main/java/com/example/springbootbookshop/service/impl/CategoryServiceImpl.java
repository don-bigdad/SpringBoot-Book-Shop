package com.example.springbootbookshop.service.impl;

import com.example.springbootbookshop.dto.category.CategoryDto;
import com.example.springbootbookshop.dto.category.CategoryRequestDto;
import com.example.springbootbookshop.entity.Category;
import com.example.springbootbookshop.exception.EntityNotFoundException;
import com.example.springbootbookshop.mapper.CategoryMapper;
import com.example.springbootbookshop.repository.CategoryRepository;
import com.example.springbootbookshop.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> findAll() {
        return categoryRepository.findAll().stream()
                .map(category -> categoryMapper.toDto(category))
                .toList();
    }

    @Override
    public CategoryDto getById(Long id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Can`t find"
                        + "category with id:" + id));
    }

    @Override
    public Category save(CategoryDto categoryDto) {
        return categoryRepository.save(categoryMapper.toEntity(categoryDto));
    }

    @Override
    public CategoryDto update(Long id, CategoryRequestDto categoryRequestDtoDto) {
        Category categoryToUpdate = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Can`t find category with id:" + id));
        categoryMapper.updateCategory(categoryRequestDtoDto,categoryToUpdate);
        categoryRepository.save(categoryToUpdate);
        return categoryMapper.toDto(categoryToUpdate);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
