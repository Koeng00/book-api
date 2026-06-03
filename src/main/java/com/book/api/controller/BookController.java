package com.book.api.controller;

import com.book.api.dto.Book.BookRequest;
import com.book.api.dto.Book.BookResponse;
import com.book.api.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.List;


@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/api/v1/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all-books")
    public ResponseEntity<List<BookResponse>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/get-book/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Long id)
    {
        return ResponseEntity.ok(bookService.getBook(id));
    }

    @PostMapping("/add-book")
    @Transactional
    public ResponseEntity<BookResponse> addBook(

            @RequestPart("book") String bookRequest,
            @RequestPart(value = "file",required = false) MultipartFile file
    ) throws IOException {
        if(file.isEmpty()) return null;
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(this.getBookRequest(bookRequest), file));
    }


    private BookRequest getBookRequest(String dtoJson) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(dtoJson, BookRequest.class);
        } catch (IOException e) {
            log.error("Exception in converting string to JSON : {}",e.getMessage());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update-book/{id}")
    public ResponseEntity<BookResponse> updateBook(
            @PathVariable Long id,
            @RequestPart("book") String request,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) throws IOException {
        if(file.isEmpty()) return null;
        return ResponseEntity.ok(bookService.updateBook(id,this.getBookRequest(request),file));
    }

    @DeleteMapping("/delete-book/{id}")
    public ResponseEntity<String> deleteBook(
            @PathVariable Long id
    ) throws IOException {
        return ResponseEntity.ok(bookService.deleteBook(id));
    }

}
