package com.example.springbootbookshop.dto.book;

import com.example.springbootbookshop.entity.Category;
import java.math.BigDecimal;
import java.util.Set;

public record BookDto(Long id,
                      String title,
                      String author,
                      String isbn,
                      BigDecimal price,
                      String description,
                      String coverImage,
                      Set<Category> categories) {
}
