package com.example.springbootbookshop.service;

import com.example.springbootbookshop.dto.BookDto;
import com.example.springbootbookshop.dto.CreateBookRequestDto;
import com.example.springbootbookshop.entity.Book;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    Book save(CreateBookRequestDto book);

    List<BookDto> findAll(Pageable pageable);

    BookDto getBookById(Long id);

    void deleteById(Long id);

    BookDto update(CreateBookRequestDto bookRequestDto, Long id);
}
