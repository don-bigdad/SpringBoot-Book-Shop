package com.example.springbootbookshop.mapper;

import com.example.springbootbookshop.config.MapperConfig;
import com.example.springbootbookshop.dto.BookDto;
import com.example.springbootbookshop.dto.CreateBookRequestDto;
import com.example.springbootbookshop.entity.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book book);

    Book bookToModel(CreateBookRequestDto bookDto);
}
