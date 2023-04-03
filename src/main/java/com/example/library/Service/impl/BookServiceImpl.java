package com.example.library.Service.impl;

import com.example.library.Repository.BookRepo;
import com.example.library.Service.BookService;
import com.example.library.model.Entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepo bookRepo;

    @Autowired
    public BookServiceImpl(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @Override
    public List<Book> getAllBooks() {
        List<Book> books = bookRepo.findAll();
        if (books.isEmpty()) {
            throw new RuntimeException("No books found");
        }
        return books;
    }

    @Override
    public Book getBookDetails(Integer bookId) {
        Optional<Book> book = bookRepo.findById(bookId);
        if (book.isEmpty()) {
            throw new RuntimeException("Book not found");
        }
        return book.get();
    }

    @Override
    public Book addBook(Book book) {
        bookRepo.save(book);
        return book;
    }

    @Override
    public Book updateBook(Book book) {
        return null;
    }

    @Override
    public Book deleteBook(Book book) {
        return null;
    }
}
