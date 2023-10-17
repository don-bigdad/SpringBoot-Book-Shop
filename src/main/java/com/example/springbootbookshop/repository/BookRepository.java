package com.example.springbootbookshop.repository;

import com.example.springbootbookshop.entity.Book;
import java.util.List;
import java.util.Optional;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();

    Optional<Book> findById(Long id);
}
