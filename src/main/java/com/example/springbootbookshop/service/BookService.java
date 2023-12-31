package com.example.springbootbookshop.service;

import com.example.springbootbookshop.dto.book.BookDto;
import com.example.springbootbookshop.dto.book.BookDtoWithoutCategoryIds;
import com.example.springbootbookshop.dto.book.CreateBookRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto book);

    List<BookDto> findAll(Pageable pageable);

    BookDto getBookById(Long id);

    void deleteById(Long id);

    BookDto update(CreateBookRequestDto bookRequestDto, Long id);

    List<BookDtoWithoutCategoryIds> getBooksByCategoryId(Long id);
}
