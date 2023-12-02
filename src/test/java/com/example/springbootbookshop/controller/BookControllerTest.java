package com.example.springbootbookshop.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.springbootbookshop.dto.book.BookDto;
import com.example.springbootbookshop.dto.book.CreateBookRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerTest {
    protected static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext,
                          @Autowired DataSource dataSource) throws SQLException {
        mockMvc = MockMvcBuilders.webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
        tearDown(dataSource);
    }

    @BeforeEach
    void beforeEach(@Autowired DataSource dataSource) throws SQLException {
        tearDown(dataSource);
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
    @DisplayName("Create a new book")
    @Sql(scripts = "classpath:database/books/clear-books-db.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createNewBookAndSaveIntoDbAssertSuccess() throws Exception {
        CreateBookRequestDto createBookRequestDto = new CreateBookRequestDto(
                "Book1",
                "Author1",
                "ISBN1",
                BigDecimal.TEN,
                "Description1",
                "coverImageUrl1",
                List.of(1L));
        BookDto expectedDto = getExpectDto(createBookRequestDto);
        String jsonRequest = objectMapper.writeValueAsString(createBookRequestDto);

        MvcResult result = mockMvc.perform(post("/books")
                .content(jsonRequest)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        BookDto actualDto = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookDto.class);
        assertNotNull(actualDto);
        EqualsBuilder.reflectionEquals(expectedDto, actualDto,"id");
    }

    @Test
    @DisplayName("Get all books")
    @WithMockUser(username = "user")
    void getAllBooksFromDbAssertSuccess() throws Exception {
        List<BookDto> expectBookDtoList = getBookDtoList();

        MvcResult result = mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto[] actualDtoList = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookDto[].class);
        assertNotNull(actualDtoList);
        for (int i = 0; i < actualDtoList.length - 1; i++) {
            EqualsBuilder.reflectionEquals(expectBookDtoList.get(i),
                    actualDtoList[i], "categoryIds");
        }
    }

    @Test
    @DisplayName("Get book by id")
    @WithMockUser(username = "user")
    void getBookByIdFromDbAssertSuccess() throws Exception {
        BookDto expectBookDto = getBookDtoList().get(1);

        MvcResult result = mockMvc.perform(get("/books/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actualDtoList = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookDto.class);
        assertNotNull(actualDtoList);
        EqualsBuilder.reflectionEquals(expectBookDto,
                    actualDtoList, "categoryIds");
    }

    @Test
    @DisplayName("Delete book by id")
    @WithMockUser(username = "admin", roles = {"ADMIN","USER"})
    void deleteBookByIdAssertSuccess() throws Exception {

        mockMvc.perform(delete("/books/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        MvcResult result = mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto[] actualDtoList = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookDto[].class);
        assertEquals(2, actualDtoList.length);
    }

    @Test
    @DisplayName("Update book by id")
    @WithMockUser(username = "admin", roles = {"ADMIN","USER"})
    void updateBookByIdAssertSuccess() throws Exception {
        MvcResult result = mockMvc.perform(get("/books/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        BookDto dto = objectMapper.readValue(result.getResponse()
                        .getContentAsString(), BookDto.class);
        assertEquals("Book 2", dto.getTitle());

        CreateBookRequestDto updatedDto = new CreateBookRequestDto(
                "Updated Title", "Updated Author", "Updated ISBN",
                BigDecimal.valueOf(29.99), "Updated Description", "updated.jpg",
                List.of(1L, 2L));

        String updatedBookJson = objectMapper.writeValueAsString(updatedDto);

        MvcResult updatedData = mockMvc.perform(put("/books/2")
                        .content(updatedBookJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        BookDto actualDtoList = objectMapper.readValue(updatedData.getResponse()
                        .getContentAsString(), BookDto.class);
        assertEquals("Updated Title", actualDtoList.getTitle());
        assertEquals("Updated Author", actualDtoList.getAuthor());
    }

    @Test
    @DisplayName("Get book by non-existing id")
    @WithMockUser(username = "user")
    void getBookByNoExitedId() throws Exception {
        mockMvc.perform(get("/books/555555")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    private List<BookDto> getBookDtoList() {
        return List.of(
                new BookDto().setId(1L).setTitle("Book 1").setAuthor("Author 1").setIsbn("ISBN-1")
                        .setPrice(BigDecimal.valueOf(19.99)).setDescription("Description 1")
                        .setCoverImage("image1.jpg"),
                new BookDto().setId(2L).setTitle("Book 2").setAuthor("Author 2").setIsbn("ISBN-2")
                        .setPrice(BigDecimal.valueOf(24.99)).setDescription("Description 2")
                        .setCoverImage("image2.jpg"),
                new BookDto().setId(3L).setTitle("Book 3").setAuthor("Author 3").setIsbn("ISBN-3")
                        .setPrice(BigDecimal.valueOf(29.99)).setDescription("Description 3")
                        .setCoverImage("image2.jpg"));
    }

    private BookDto getExpectDto(CreateBookRequestDto dto) {
        return new BookDto()
                .setAuthor(dto.author())
                .setIsbn(dto.isbn())
                .setId(1L)
                .setTitle(dto.title())
                .setDescription(dto.description())
                .setCategoryIds(dto.categoryIds())
                .setCoverImage(dto.coverImage())
                .setPrice(dto.price());
    }
}