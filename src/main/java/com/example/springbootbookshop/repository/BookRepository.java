package com.example.springbootbookshop.repository;

import com.example.springbootbookshop.entity.Book;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b JOIN b.categories c WHERE c.id = :categoryId")
    List<Book> findAllByCategoryId(Long categoryId);
}
