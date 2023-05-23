package com.example.library.Service.impl;

import com.example.library.Repository.AuthorRepo;
import com.example.library.Repository.BookRepo;
import com.example.library.Repository.CategoryRepo;
import com.example.library.Service.BookService;
import com.example.library.model.DTO.BookDTO;
import com.example.library.model.Entity.Author;
import com.example.library.model.Entity.Book;
import com.example.library.model.Entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepo bookRepo;
    private final AuthorRepo authorRepo;
    private final CategoryRepo categoryRepo;

    @Autowired
    public BookServiceImpl(BookRepo bookRepo, AuthorRepo authorRepo, CategoryRepo categoryRepo) {
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
        this.categoryRepo = categoryRepo;
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
    public Book addBook(BookDTO bookDTO) {
        Optional<Author> author = authorRepo.findByName(bookDTO.getAuthorName());

        if (author.isEmpty()) {
            Author newAuthor = new Author();
            newAuthor.setName(bookDTO.getAuthorName());
            authorRepo.save(newAuthor);
            author = Optional.of(newAuthor);
        }

        List<Category> categories = bookDTO.getCategories().stream().map(
                (categoryId) -> {
                    Optional<Category> category = categoryRepo.findById(categoryId);
                    if (category.isEmpty()) {
                        throw new RuntimeException("Category not found");
                    }
                    return category.get();
                }
        ).toList();

        Book book = new Book();
        book.setTitle(bookDTO.getTitle());
        book.setCover(bookDTO.getCover());
        book.setDescription(bookDTO.getDescription());
        book.setReleaseDate(bookDTO.getReleaseDate());
        book.setPages(bookDTO.getPages());
        book.setPrice(bookDTO.getPrice());
        book.setAuthor(author.get());
        book.setQuantity(bookDTO.getQuantity());
        book.setCategories(categories);
        bookRepo.save(book);
        return book;
    }

    @Override
    public Book updateBook(BookDTO bookDTO) {
        Optional<Author> author = authorRepo.findByName(bookDTO.getAuthorName());

        if (author.isEmpty()) {
            Author newAuthor = new Author();
            newAuthor.setName(bookDTO.getAuthorName());
            authorRepo.save(newAuthor);
            author = Optional.of(newAuthor);
        }

        List<Category> categories = bookDTO.getCategories().stream().map(
                (categoryId) -> {
                    Optional<Category> category = categoryRepo.findById(categoryId);
                    if (category.isEmpty()) {
                        throw new RuntimeException("Category not found");
                    }
                    return category.get();
                }
        ).toList();

        Book book = bookRepo.findById(bookDTO.getId()).get();
        book.setTitle(bookDTO.getTitle());
        book.setCover(bookDTO.getCover());
        book.setDescription(bookDTO.getDescription());
        book.setReleaseDate(bookDTO.getReleaseDate());
        book.setPages(bookDTO.getPages());
        book.setPrice(bookDTO.getPrice());
        book.setAuthor(author.get());
        book.setQuantity(bookDTO.getQuantity());
        book.setCategories(categories);
        bookRepo.save(book);
        return book;
    }



    @Override
    public Book deleteBook(Integer bookId) {
        bookRepo.deleteById(bookId);
        return null;
    }

    @Override
    public List<Book> getBooksByCategory(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).get();
        return category.getBooks();
    }

    @Override
    public List<Book> getBooksByAuthor(Integer authorId) {
        Author author = authorRepo.findById(authorId).get();
        return author.getBooks();
    }
}
