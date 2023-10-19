package com.example.springbootbookshop.controller;

import com.example.springbootbookshop.dto.BookDto;
import com.example.springbootbookshop.dto.CreateBookRequestDto;
import com.example.springbootbookshop.entity.Book;
import com.example.springbootbookshop.service.BookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@Tag(name = "Book management",description = "Endpoints for managing books")
@RestController
@Validated
@RequestMapping(value = "/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    @Operation(summary = "Get all books from DB")
    @ResponseStatus(HttpStatus.OK)
    public List<BookDto> getAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Delete book by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookById(@PathVariable Long id) {
        bookService.deleteById(id);
    }

    @GetMapping(value = "/{id}")
    @Operation(summary = "Get book by Id from DB")
    @ResponseStatus(HttpStatus.OK)
    public BookDto getBookById(@PathVariable @Positive Long id) {
        return bookService.getBookById(id);
    }

    @PutMapping(value = "/{id}")
    @Operation(summary = "Update book by Id in DB")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public BookDto getBookById(@RequestBody @Valid CreateBookRequestDto bookDto,
                               @PathVariable Long id) {
        return bookService.update(bookDto, id);
    }

    @PostMapping
    @Operation(summary = "Create new book")
    public Book createBook(@RequestBody @Valid CreateBookRequestDto bookDto) {
        return bookService.save(bookDto);
    }
}
