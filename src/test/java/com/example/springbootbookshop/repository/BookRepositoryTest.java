package com.example.springbootbookshop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.springbootbookshop.entity.Book;
import com.example.springbootbookshop.entity.Category;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Find book where id 1 and 2")
    @Sql(scripts = "classpath:database/books/insert-books.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books/clear-books-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getBookByIdTestAssertSuccess() {
        Optional<Book> book1 = bookRepository.findById(1L);
        Optional<Book> book2 = bookRepository.findById(2L);
        assertEquals(1L,book1.get().getId());
        assertEquals(2L,book2.get().getId());
        assertEquals("Book 1",book1.get().getTitle());
    }

    @Test
    @DisplayName("Find all books where category id 1 and 2")
    @Sql(scripts = {"classpath:database/books/insert-books.sql",
            "classpath:database/category/insert-category.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = {"classpath:database/books/clear-books-db.sql",
            "classpath:database/category/clear-category.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllBooksByCategoryId() {
        Book book1 = bookRepository.findById(1L).get();
        Book book2 = bookRepository.findById(2L).get();
        Book book3 = bookRepository.findById(3L).get();

        List<Category> categories = categoryRepository.findAll();

        book1.setCategories(Set.of(categories.get(0),categories.get(1)));
        book2.setCategories(Set.of(categories.get(1)));
        book3.setCategories(Set.of(categories.get(2)));

        List<Book> booksInSecondCategory = bookRepository.findAllByCategoryId(2L);
        List<Book> booksInFirstCategory = bookRepository.findAllByCategoryId(1L);
        assertEquals(2, booksInSecondCategory.size());
        assertEquals(1, booksInFirstCategory.size());
    }

    @Test
    @DisplayName("Find all  books")
    @Sql(scripts = "classpath:database/books/insert-books.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books/clear-books-db.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getAllBooksFromDbAssertSuccess() {
        Page<Book> books = bookRepository.findAll(PageRequest.of(0, 5));
        assertEquals(3L, books.getTotalElements());
    }
}
