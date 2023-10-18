package com.example.springbootbookshop.service.impl;

import com.example.springbootbookshop.dto.BookDto;
import com.example.springbootbookshop.dto.CreateBookRequestDto;
import com.example.springbootbookshop.entity.Book;
import com.example.springbootbookshop.mapper.BookMapper;
import com.example.springbootbookshop.repository.BookRepository;
import com.example.springbootbookshop.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    @Override
    public Book save(CreateBookRequestDto book) {
        return bookRepository.save(bookMapper.toBook(book));
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream()
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
        Optional<Book> bookOptional = bookRepository.findById(id);
        if (bookOptional.isPresent()) {
            Book book = bookMapper.toBook(bookRequestDto);
            book.setId(id);
            bookRepository.save(book);
            return bookMapper.toDto(book);
        } else {
            throw new EntityNotFoundException("Book with ID " + id + " not found");
        }
    }
}
