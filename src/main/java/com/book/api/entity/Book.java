package com.book.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@ToString
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Title is required!")
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Author is required!")
    private String author;

    private String description;

    @Column(nullable = false)
    @NotBlank(message = "Category is required!")
    private String category;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;

    @Column(name = "book_cover")
    private String bookCover;

    @Column(name = "book_cover_url")
    private String bookCoverUrl;

    @Column(updatable = false)
    private LocalDateTime createdAT;

    @Column(insertable = false)
    private LocalDateTime updatedAT;

    @PrePersist
    void prePersist() {
        this.createdAT = LocalDateTime.now();
        this.updatedAT = null;
    }

    @PreUpdate
    void preUpdate() {
        this.updatedAT = LocalDateTime.now();
    }
}
