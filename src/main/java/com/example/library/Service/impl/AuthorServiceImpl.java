package com.example.library.Service.impl;

import com.example.library.Exception.NotFoundException;
import com.example.library.Repository.AuthorRepo;
import com.example.library.Service.AuthorService;
import com.example.library.model.Entity.Author;
import com.example.library.model.Entity.Book;

import java.util.List;

public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepo authorRepo;

    public AuthorServiceImpl(AuthorRepo authorRepo) {
        this.authorRepo = authorRepo;
    }


    @Override
    public List<Book> getBookByAuthor(Integer authorId) {
        return null;
    }

    @Override
    public List<Author> getAllAuthors() {
        List<Author> authors = authorRepo.findAll();
        if(authors.isEmpty())
            throw new NotFoundException("No authors found");
        return authors;
    }
}
