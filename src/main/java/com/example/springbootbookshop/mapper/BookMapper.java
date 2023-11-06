package com.example.springbootbookshop.mapper;

import com.example.springbootbookshop.dto.book.BookDto;
import com.example.springbootbookshop.dto.book.BookDtoWithoutCategoryIds;
import com.example.springbootbookshop.dto.book.CreateBookRequestDto;
import com.example.springbootbookshop.entity.Book;
import com.example.springbootbookshop.entity.Category;
import org.mapstruct.AfterMapping;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        implementationPackage = "<PACKAGE_NAME>.impl")
public interface BookMapper {
    BookDto toDto(Book book);

    Book toBook(CreateBookRequestDto bookDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBook(CreateBookRequestDto dto, @MappingTarget Book book);

    BookDtoWithoutCategoryIds toDtoWithoutCategories(Book book);

    @AfterMapping
    default void setCategoryIds(@MappingTarget BookDto bookDto, Book book) {
        bookDto.setCategoryIds(book.getCategories().stream()
                .map(Category::getId)
                .toList());
    }
}
