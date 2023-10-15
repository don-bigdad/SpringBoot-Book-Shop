package com.example.springbootbookshop.service;

import com.example.springbootbookshop.entity.Book;
import java.util.List;

public interface BookService {
    Book save(Book book);

    List<Book> findAll();
}
