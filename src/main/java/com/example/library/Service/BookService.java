package com.example.library.Service;

import com.example.library.model.DTO.BookDTO;
import com.example.library.model.DTO.BookHomeDto;
import com.example.library.model.Entity.Book;

import java.util.List;

public interface BookService {
    public List<BookHomeDto> getAllBooksHome();
    public Book getBookDetails(Integer bookId);
    public Book addBook(BookDTO book);
    public Book updateBook(BookDTO book);
    public Book deleteBook(Integer bookId);
    public List<Book> getBooksByCategory(Integer categoryId);
    public List<Book> getBooksByAuthor(Integer authorId);

    List<BookHomeDto> getBookByCategoryId(Integer categoryId);

    List<BookHomeDto> getBookByAuthorId(Integer authorId);

    List<BookHomeDto> searchBook(String keyword);

    List<BookDTO> getAllBooks();
}
