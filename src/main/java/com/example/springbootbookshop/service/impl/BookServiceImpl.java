package com.example.springbootbookshop.service.impl;

import com.example.springbootbookshop.dto.BookDto;
import com.example.springbootbookshop.dto.CreateBookRequestDto;
import com.example.springbootbookshop.entity.Book;
import com.example.springbootbookshop.mapper.BookMapper;
import com.example.springbootbookshop.repository.BookRepository;
import com.example.springbootbookshop.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public Book save(CreateBookRequestDto book) {
        return bookRepository.save(bookMapper.bookToModel(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto getBookById(Long id) {
        return bookMapper.toDto(bookRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(
                        "Can`t get book with id from DB: " + id)));
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
