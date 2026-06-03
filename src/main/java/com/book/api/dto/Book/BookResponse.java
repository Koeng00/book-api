package com.book.api.dto.Book;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record BookResponse(
        Long id,
        String title,
        String author,
        String description,
        String category,
        Double price,
        Integer quantity,
        @JsonProperty("book_cover")
        String bookCover,
        @JsonProperty("book_cover_url")
        String bookCoverUrl,
        @JsonProperty("created_at")
        LocalDateTime createdAt
) {
}
