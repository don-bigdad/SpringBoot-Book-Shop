package com.example.springbootbookshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.springbootbookshop.dto.category.CategoryDto;
import com.example.springbootbookshop.dto.category.CategoryRequestDto;
import com.example.springbootbookshop.entity.Category;
import com.example.springbootbookshop.mapper.CategoryMapper;
import com.example.springbootbookshop.mapper.impl.CategoryMapperImpl;
import com.example.springbootbookshop.repository.CategoryRepository;
import com.example.springbootbookshop.service.impl.CategoryServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Spy
    private CategoryMapper categoryMapper = new CategoryMapperImpl();

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Find all categories test")
    public void getAllCategories() {
        List<Category> categories = getDefaultCategories();
        List<CategoryDto> dtos = categories.stream()
                .map(categoryMapper::toDto)
                .toList();
        when(categoryRepository.findAll()).thenReturn(categories);

        List<CategoryDto> result = categoryService.findAll();

        assertEquals(result, dtos);
        assertEquals(result.get(0), dtos.get(0));
        assertEquals(result.get(0), dtos.get(0));
    }

    @Test
    @DisplayName("Get category by id")
    public void getCategoryByIdExpectSuccess() {
        List<Category> categories = getDefaultCategories();
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(categories.get(0)));
        CategoryDto expected = categoryMapper.toDto(categories.get(0));
        CategoryDto actual = categoryService.getById(1L);
        categoryService.getById(1L);
        assertEquals(actual, expected);
        verify(categoryRepository, times(2)).findById(1L);
    }

    @Test
    @DisplayName("Create a new category")
    public void saveNewCategorySuccess() {
        CategoryRequestDto firstCategory = new CategoryRequestDto(
                "Category1",
                "Description1");
        Category entity = categoryMapper.toEntity(firstCategory);
        when(categoryRepository.save(entity)).thenReturn(entity);
        categoryService.save(firstCategory);
        verify(categoryRepository, times(1)).save(entity);
    }

    @Test
    @DisplayName("Update category")
    public void updateCategoryAssertSuccess() {
        Category categoryToUpdate = getDefaultCategories().get(0);
        String oltName = "Category1";
        assertEquals(oltName, categoryToUpdate.getName());
        CategoryRequestDto requestDto = new CategoryRequestDto(
                "Science",
                "Category with books about science");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(categoryToUpdate));
        categoryService.update(1L, requestDto);
        categoryMapper.updateCategory(requestDto, categoryToUpdate);

        assertEquals("Science", categoryToUpdate.getName());
        assertEquals("Category with books about science", categoryToUpdate.getDescription());

        CategoryRequestDto newRequest = new CategoryRequestDto(
                "Nature",
                "Category with books about nature");
        categoryService.update(1L, newRequest);
        assertEquals("Nature", categoryToUpdate.getName());
        verify(categoryRepository,times(2)).findById(1L);
    }

    @Test
    @DisplayName("Delete existing category by id")
    public void deleteExistingCategoryById() {

        when(categoryRepository.existsById(1L)).thenReturn(true);

        categoryService.deleteById(1L);

        verify(categoryRepository).existsById(1L);
        verify(categoryRepository,times(1)).deleteById(1L);
    }

    private List<Category> getDefaultCategories() {
        Category category1 = new Category()
                .setName("Category1")
                .setDescription("Description1")
                .setId(1L);
        Category category2 = new Category()
                .setName("Category2")
                .setDescription("Description2")
                .setId(2L);
        return List.of(category1, category2);
    }
}
