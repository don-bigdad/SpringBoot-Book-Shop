package com.example.springbootbookshop.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.springbootbookshop.dto.book.BookDto;
import com.example.springbootbookshop.dto.book.BookDtoWithoutCategoryIds;
import com.example.springbootbookshop.dto.book.CreateBookRequestDto;
import com.example.springbootbookshop.entity.Book;
import com.example.springbootbookshop.entity.Category;
import com.example.springbootbookshop.mapper.BookMapper;
import com.example.springbootbookshop.mapper.impl.BookMapperImpl;
import com.example.springbootbookshop.repository.BookRepository;
import com.example.springbootbookshop.repository.CategoryRepository;
import com.example.springbootbookshop.service.impl.BookServiceImpl;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private CategoryRepository categoryRepository;

    @Spy
    private BookMapper bookMapper = new BookMapperImpl();

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Get book by id")
    void getBookByExistId() {
        Book expect = getDefaultBook();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(expect));
        BookDto actual = bookService.getBookById(1L);
        assertEquals(actual, bookMapper.toDto(expect));
        assertEquals(expect.getPrice(),actual.getPrice());
    }

    @Test
    @DisplayName("Save book")
    void saveBook() {
        CreateBookRequestDto dto = getCreateRequestBookDto();
        bookService.save(dto);
        bookService.save(dto);
        verify(bookRepository, times(2)).save(any());
    }

    @Test
    @DisplayName("Delete book by id")
    void deleteBookById() {
        Long bookId = 1L;
        bookService.deleteById(bookId);
        verify(bookRepository, times(1)).deleteById(bookId);
    }

    @Test
    @DisplayName("Get books by category id")
    void getAllBooksByExistCategory() {
        Book book1 = getDefaultBook();
        Book book2 = getDefaultBook();
        book2.setId(2L);
        List<Book> actualBookList = List.of(book1,book2);
        when(categoryRepository.existsById(1L)).thenReturn(true);
        when(bookRepository.findAllByCategoryId(1L))
                .thenReturn(List.of(book1,book2));
        List<BookDtoWithoutCategoryIds> booksByCategoryId = bookService.getBooksByCategoryId(1L);
        assertEquals(booksByCategoryId.size(), actualBookList.size());
    }

    @Test
    @DisplayName("Update book")
    void updateBookAssertSuccess() {
        Book bookToUpdate = getDefaultBook();
        BigDecimal oldPrice = BigDecimal.TEN;
        assertEquals(oldPrice, bookToUpdate.getPrice());
        String actualTitle = "New Title";
        CreateBookRequestDto bookRequestDto = getCreateRequestBookDto();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(bookToUpdate));
        bookService.update(bookRequestDto,1L);
        assertEquals(actualTitle, bookToUpdate.getTitle());
        assertEquals(BigDecimal.valueOf(200), bookToUpdate.getPrice());
        verify(bookRepository,times(1)).findById(1L);
    }

    @Test
    @DisplayName("Find all books")
    void findAllBooks() {
        Book book1 = getDefaultBook();
        Book book2 = getDefaultBook();
        book2.setId(2L);
        List<Book> books = List.of(book1, book2);

        Page<Book> bookPage = new PageImpl<>(books);

        when(bookRepository.findAll((Pageable) any())).thenReturn(bookPage);
        List<BookDto> actualBooks = bookService.findAll(PageRequest.of(0, 10));

        assertEquals(books.size(), actualBooks.size());

        for (int i = 0; i < books.size(); i++) {
            Book expectedBook = books.get(i);
            BookDto actualBookDto = actualBooks.get(i);

            assertEquals(bookMapper.toDto(expectedBook), actualBookDto);
            assertEquals(expectedBook.getPrice(), actualBookDto.getPrice());
        }
    }

    private Book getDefaultBook() {
        Book book = new Book();
        book.setId(1L);
        book.setIsbn("ISBN1");
        book.setAuthor("Author1");
        book.setPrice(BigDecimal.TEN);
        book.setTitle("Book1");
        book.setDescription("Description1");
        book.setCategories(getDefaultCategories());
        return book;
    }

    private CreateBookRequestDto getCreateRequestBookDto() {
        return new CreateBookRequestDto(
                "New Title",
                "New Author",
                "New ISBN",
                BigDecimal.valueOf(200),
                "New Description",
                "New image url",
                List.of(1L));
    }

    private Set<Category> getDefaultCategories() {
        Category category1 = new Category()
                .setName("Category1")
                .setDescription("Description1")
                .setId(1L);
        Category category2 = new Category()
                .setName("Category2")
                .setDescription("Description2")
                .setId(2L);
        return Set.of(category1, category2);
    }
}
