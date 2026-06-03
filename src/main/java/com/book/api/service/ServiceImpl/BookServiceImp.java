package com.book.api.service.ServiceImpl;

import com.book.api.dto.Book.BookRequest;
import com.book.api.dto.Book.BookResponse;
import com.book.api.entity.Book;
import com.book.api.mapper.BookMapper;
import com.book.api.repository.BookRepository;
import com.book.api.service.BookService;
import com.book.api.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImp implements BookService {

    @Value("${project.images}")
    private String path;

    @Value("${base.url}")
    private String baseUrl;

    private final BookRepository bookRepository;
    private final FileService fileService;

    @Override
    public BookResponse addBook(BookRequest request, MultipartFile file) throws IOException {
        if(Files.exists(Paths.get(path + File.separator + file.getOriginalFilename()))) {
            throw new FileAlreadyExistsException("File already existed! Please give another file!");
        }

        String uploadFileName = fileService.uploadFile(path, file);
        String bookCoverUrl = baseUrl + "/api/v1/files/" + uploadFileName;

        Book book = BookMapper.toEntity(request, uploadFileName, bookCoverUrl);
        Book savedBook = bookRepository.save(book);
        return BookMapper.toResponse(savedBook);
    }

    @Transactional
    @Override
    public BookResponse updateBook(Long id, BookRequest request, MultipartFile file) throws IOException {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id : " + id));
        String bookCover = book.getBookCover();
        String bookCoverUrl = book.getBookCoverUrl();

        if( file != null ) {
            Files.deleteIfExists(Paths.get(path + File.separator + bookCover));
            bookCover = fileService.uploadFile(path, file);
            bookCoverUrl = baseUrl + "/api/v1/files/" + bookCover;
        }
        BookMapper.updateEntity(book, request, bookCover, bookCoverUrl);
        Book updatedBook = bookRepository.save(book);

        return BookMapper.toResponse(updatedBook);
    }


    @Override
    public BookResponse getBook(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        return BookMapper.toResponse(book);
    }

    @Override
    public List<BookResponse> getAllBooks() {
        return bookRepository.findAll().stream().map(BookMapper::toResponse).toList();
    }


    @Override
    public String deleteBook(Long id) throws IOException{
        Book book = bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found with id: " + id));
        Files.deleteIfExists(Paths.get(path + File.separator + book.getBookCover()));
        bookRepository.delete(book);
        return "Book Deleted successful with id : " + id;
    }
}
