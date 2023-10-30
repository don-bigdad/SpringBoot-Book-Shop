package com.example.springbootbookshop.repository;

import com.example.springbootbookshop.dto.book.BookDto;
import com.example.springbootbookshop.entity.Book;
import com.example.springbootbookshop.entity.Category;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<BookDto> getBookByCategory(Category category);
}
