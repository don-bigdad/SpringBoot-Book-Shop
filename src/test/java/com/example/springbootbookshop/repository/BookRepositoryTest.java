package com.example.springbootbookshop.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.springbootbookshop.entity.Book;
import com.example.springbootbookshop.entity.Category;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.datasource.init.ScriptUtils;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeAll
    static void beforeAll(@Autowired DataSource dataSource) {
        tearDown(dataSource);
    }

    @BeforeEach
    void beforeEach(@Autowired DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(connection,
                    new ClassPathResource("database/category/insert-category.sql"));
            ScriptUtils.executeSqlScript(connection,
                    new ClassPathResource("database/books/insert-books.sql"));
        }
    }

    @AfterEach
    void afterEach(@Autowired DataSource dataSource) {
        tearDown(dataSource);
    }

    @SneakyThrows
    static void tearDown(DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            connection.setAutoCommit(true);
            ScriptUtils.executeSqlScript(connection,
                    new ClassPathResource("database/category/clear-category.sql"));
            ScriptUtils.executeSqlScript(connection,
                    new ClassPathResource("database/books/clear-books-db.sql"));
        }
    }

    @Test
    @DisplayName("Find book where id 1 and 2")
    void getBookByIdTestAssertSuccess() {
        Optional<Book> book1 = bookRepository.findById(1L);
        Optional<Book> book2 = bookRepository.findById(2L);
        assertEquals(1L,book1.get().getId());
        assertEquals(2L,book2.get().getId());
        assertEquals("Book 1",book1.get().getTitle());
    }

    @Test
    @DisplayName("Find all books where category id 1 and 2")
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
    void getAllBooksFromDbAssertSuccess() {
        Page<Book> books = bookRepository.findAll(PageRequest.of(0, 5));
        assertEquals(3L, books.getTotalElements());
    }
}
