package com.example.library.Service;

import com.example.library.model.Entity.Author;
import com.example.library.model.Entity.Book;

import java.util.List;

public interface AuthorService {
    public List<Book> getBookByAuthor(Integer authorId);
    public List<Author> getAllAuthors();
}
