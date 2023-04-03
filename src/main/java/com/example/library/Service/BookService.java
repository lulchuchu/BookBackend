package com.example.library.Service;

import com.example.library.model.Entity.Book;

import java.util.List;

public interface BookService {
    public List<Book> getAllBooks();
    public Book getBookDetails(Integer bookId);
    public Book addBook(Book book);
    public Book updateBook(Book book);
    public Book deleteBook(Book book);

}
