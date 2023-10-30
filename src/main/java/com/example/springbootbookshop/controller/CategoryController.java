package com.example.springbootbookshop.controller;

import com.example.springbootbookshop.dto.category.CategoryDto;
import com.example.springbootbookshop.entity.Book;
import com.example.springbootbookshop.service.BookService;
import com.example.springbootbookshop.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Tag(name = "Categories management",description = "Endpoints for managing categories")
@RestController
@Validated
@RequestMapping(value = "/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final BookService bookService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Create new category (for admin only)")
    public CategoryDto createCategory(@RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.save(categoryDto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all categories from DB")
    public List<CategoryDto> getAll() {
        return categoryService.findAll();
    }


    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get category by id from DB")
    @PreAuthorize("hasRole('ROLE_USER')")
    public CategoryDto getCategoryById(@PathVariable @Positive Long id) {
        return categoryService.getById(id);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update category by id (for Admin only)")
    public CategoryDto updateCategory(@PathVariable @Positive Long id,
                                      @RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.update(id,categoryDto);
    }
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Delete category by id (for Admin only)")
    public void deleteCategory(Long id) {
        categoryService.deleteById(id);
    }

    @GetMapping("/{id}/books")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Get books by category")
    public List<Book> getBooksByCategoryId(Long id) {
        return bookService.getBooksByCategoryName(id);
    }
}
