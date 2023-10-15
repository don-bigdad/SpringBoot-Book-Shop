package com.example.springbootbookshop;

import com.example.springbootbookshop.entity.Book;
import com.example.springbootbookshop.service.BookService;
import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringBootBookShopApplication {
    @Autowired
    private BookService bookService;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootBookShopApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            Book book = new Book();
            book.setDescription("someBook");
            book.setPrice(BigDecimal.TEN);
            book.setTitle("Big Bang Theory");
            book.setAuthor("Author1");
            book.setIsbn("Something");
            book.setCoverImage("SomeImage");

            bookService.save(book);

            System.out.println(bookService.findAll());
        };
    }
}
