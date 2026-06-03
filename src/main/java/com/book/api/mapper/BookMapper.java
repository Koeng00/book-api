package com.book.api.mapper;

import com.book.api.dto.Book.BookRequest;
import com.book.api.dto.Book.BookResponse;
import com.book.api.entity.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {

    public static Book toEntity(BookRequest bookRequest, String bookCover, String bookCoverUrl) {
        return Book.builder()
                .author(bookRequest.author())
                .title(bookRequest.title())
                .description(bookRequest.description())
                .price(bookRequest.price())
                .category(bookRequest.category())
                .bookCover(bookCover)
                .bookCoverUrl(bookCoverUrl)
                .quantity(bookRequest.quantity())
                .build();
    }

    public static void updateEntity(Book book, BookRequest bookRequest, String bookCover, String bookCoverUrl) {
        book.setAuthor(bookRequest.author());
        book.setTitle(bookRequest.title());
        book.setPrice(bookRequest.price());
        book.setDescription(bookRequest.description());
        book.setQuantity(bookRequest.quantity());
        book.setCategory(bookRequest.category());
        book.setBookCover(bookCover);
        book.setBookCoverUrl(bookCoverUrl);
    }

    public static BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getDescription(),
                book.getCategory(),
                book.getPrice(),
                book.getQuantity(),
                book.getBookCover(),
                book.getBookCoverUrl(),
                book.getCreatedAT()
        );
    }
}
