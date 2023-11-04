package com.example.springbootbookshop.service.impl;

import com.example.springbootbookshop.dto.book.BookDto;
import com.example.springbootbookshop.dto.book.BookDtoWithoutCategoryIds;
import com.example.springbootbookshop.dto.book.CreateBookRequestDto;
import com.example.springbootbookshop.entity.Book;
import com.example.springbootbookshop.entity.Category;
import com.example.springbootbookshop.exception.EntityNotFoundException;
import com.example.springbootbookshop.mapper.BookMapper;
import com.example.springbootbookshop.repository.BookRepository;
import com.example.springbootbookshop.repository.CategoryRepository;
import com.example.springbootbookshop.service.BookService;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public BookDto save(CreateBookRequestDto bookDto) {
        Set<Category> categories = getCategories(bookDto);
        Book book = bookMapper.toBook(bookDto);
        book.setCategories(categories);
        return bookMapper.toDto(bookRepository.save(book));
    }

    @Override
    public List<BookDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto getBookById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Can`t find"
                        + "book with id:" + id));
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public BookDto update(CreateBookRequestDto bookRequestDto, Long id) {
        Book bookToUpdate = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can`t find book with id:" + id));
        Set<Category> categories = getCategories(bookRequestDto);
        bookMapper.updateBook(bookRequestDto, bookToUpdate);
        bookToUpdate.setCategories(categories);
        return bookMapper.toDto(bookRepository.save(bookToUpdate));
    }

    @Override
    public List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id) {
        if (categoryRepository.existsById(id)) {
            return bookRepository.findAllByCategoryId(id).stream()
                    .map(bookMapper::toDtoWithoutCategories)
                    .toList();
        }
        throw new EntityNotFoundException("Category with id " + id
                + " doesn`t exist");
    }

    private Set<Category> getCategories(CreateBookRequestDto bookDto) {
        return bookDto.categoryIds().stream()
                .map(id -> categoryRepository.findById(id).orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }
}
