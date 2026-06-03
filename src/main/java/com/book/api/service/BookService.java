package com.book.api.service;

import com.book.api.dto.Book.BookRequest;
import com.book.api.dto.Book.BookResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.List;

@Service
public interface BookService {

    BookResponse getBook(Long id);
    List<BookResponse> getAllBooks();
    BookResponse addBook(BookRequest request, MultipartFile file) throws FileAlreadyExistsException, IOException;
    BookResponse updateBook(Long id, BookRequest request,  MultipartFile file) throws IOException;
    String deleteBook(Long id) throws IOException;
}
