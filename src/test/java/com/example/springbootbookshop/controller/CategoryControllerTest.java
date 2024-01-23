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
import com.example.springbootbookshop.dto.book.BookDtoWithoutCategoryIds;
import com.example.springbootbookshop.dto.category.CategoryDto;
import com.example.springbootbookshop.dto.category.CategoryRequestDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class CategoryControllerTest {
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
        }
    }

    @Test
    @DisplayName("Create a new category")
    @Sql(scripts = "classpath:database/category/clear-category.sql",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void createNewCategoryAndSaveIntoDbAssertSuccess() throws Exception {
        CategoryRequestDto categoryRequestDto = new CategoryRequestDto(
                "Literature",
                "Category with different stories");
        String jsonRequest = objectMapper.writeValueAsString(categoryRequestDto);

        MvcResult result = mockMvc.perform(post("/category")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        BookDto actualDto = objectMapper.readValue(result.getResponse().getContentAsString(),
                BookDto.class);
        assertNotNull(actualDto);
        EqualsBuilder.reflectionEquals(categoryRequestDto, actualDto,"id");
    }

    @Test
    @DisplayName("Get all categories")
    @WithMockUser(username = "user")
    void getAllCategoriesFromDbAssertSuccess() throws Exception {
        List<CategoryDto> expectedCategoryDtoList = getCategoriesList();

        MvcResult result = mockMvc.perform(get("/category")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<CategoryDto> actualDtoList = objectMapper.readValue(result.getResponse()
                        .getContentAsString(), new TypeReference<>() {});
        assertNotNull(actualDtoList);
        for (int i = 0; i < actualDtoList.size() - 1; i++) {
            EqualsBuilder.reflectionEquals(expectedCategoryDtoList.get(i),
                    actualDtoList.get(i), "id");
        }
    }

    @Test
    @DisplayName("Delete book by id")
    @WithMockUser(username = "admin", roles = {"ADMIN","USER"})
    void deleteCategoryByIdAssertSuccess() throws Exception {

        mockMvc.perform(delete("/category/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        MvcResult result = mockMvc.perform(get("/category")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto[] actualDtoList = objectMapper.readValue(result.getResponse()
                        .getContentAsString(), CategoryDto[].class);
        assertEquals(2, actualDtoList.length);
    }

    @Test
    @DisplayName("User try delete category by id")
    @WithMockUser(username = "user")
    void deleteCategoryByIdWithNoPermissionAssertException() throws Exception {
        mockMvc.perform(delete("/category/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Update category by id")
    @WithMockUser(username = "admin", roles = {"ADMIN","USER"})
    void updateBookByIdAssertSuccess() throws Exception {
        MvcResult result = mockMvc.perform(get("/category/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        CategoryDto dto = objectMapper.readValue(result.getResponse()
                .getContentAsString(), CategoryDto.class);
        assertEquals("Category 2", dto.name());

        CategoryRequestDto updateRequest = new CategoryRequestDto(
                "Updated name",
                "Updated description"
        );

        String updatedBookJson = objectMapper.writeValueAsString(updateRequest);

        MvcResult updatedData = mockMvc.perform(put("/category/2")
                        .content(updatedBookJson)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CategoryDto actualDtoList = objectMapper.readValue(updatedData.getResponse()
                .getContentAsString(), CategoryDto.class);
        assertEquals("Updated name", actualDtoList.name());
        assertEquals("Updated description", actualDtoList.description());
    }

    @Test
    @DisplayName("Get all books by category id")
    @Sql(scripts = {"classpath:database/books/insert-new-books.sql",
            "classpath:database/books-categories/insert-books-categories.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "classpath:database/books-categories/clear-book-category.sql",
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithMockUser(username = "user")
    void getAllBooksByCategoryAssertSuccess() throws Exception {
        MvcResult result = mockMvc.perform(get("/category/2/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        List<BookDtoWithoutCategoryIds> bookDtoWithoutCategoryIds = objectMapper.readValue(
                result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertEquals(2, bookDtoWithoutCategoryIds.size());
        assertEquals("Book 5", bookDtoWithoutCategoryIds.get(1).title());
    }

    private CategoryDto getExpectDto() {
        return new CategoryDto("Literature",
                "Category with different stories");
    }

    private List<CategoryDto> getCategoriesList() {
        return List.of(new CategoryDto("Category1", "Description1"),
                new CategoryDto("Category2", "Description2"));
    }
}
