package com.example.springbootbookshop.repository;

import com.example.springbootbookshop.entity.Book;
import java.util.List;

public interface BookRepository {
    Book save(Book book);

    List<Book> findAll();
}
