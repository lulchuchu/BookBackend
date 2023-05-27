package com.example.library.Service.impl;

import com.example.library.Repository.AuthorRepo;
import com.example.library.Repository.BookRepo;
import com.example.library.Repository.CategoryRepo;
import com.example.library.Service.BookService;
import com.example.library.model.DTO.BookDTO;
import com.example.library.model.DTO.BookHomeDto;
import com.example.library.model.Entity.Author;
import com.example.library.model.Entity.Book;
import com.example.library.model.Entity.Category;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookServiceImpl implements BookService {
    private final BookRepo bookRepo;
    private final AuthorRepo authorRepo;
    private final CategoryRepo categoryRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public BookServiceImpl(BookRepo bookRepo, AuthorRepo authorRepo, CategoryRepo categoryRepo, ModelMapper modelMapper) {
        this.bookRepo = bookRepo;
        this.authorRepo = authorRepo;
        this.categoryRepo = categoryRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<BookHomeDto> getAllBooksHome() {
        List<Book> books = bookRepo.findAll();
        if (books.isEmpty()) {
            throw new RuntimeException("No books found");
        }
        return books.stream().map(
                (book) -> {
                    BookHomeDto bookHomeDto = modelMapper.map(book, BookHomeDto.class);
                    bookHomeDto.setAuthorName(book.getAuthor().getName());
                    return bookHomeDto;
                }
        ).toList();
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
                (categoryName) -> {
                    Optional<Category> category = categoryRepo.findByName(categoryName);
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
                (categoryName) -> {
                    Optional<Category> category = categoryRepo.findByName(categoryName);
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
    public List<Book> getBooksByCategory(Integer categoryName) {
        Category category = categoryRepo.findById(categoryName).get();
        return category.getBooks();
    }

    @Override
    public List<Book> getBooksByAuthor(Integer authorId) {
        Author author = authorRepo.findById(authorId).get();
        return author.getBooks();
    }

    @Override
    public List<BookHomeDto> getBookByCategoryId(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).get();
        return category.getBooks().stream().map(
                (book) -> {
                    BookHomeDto bookHomeDto = modelMapper.map(book, BookHomeDto.class);
                    bookHomeDto.setAuthorName(book.getAuthor().getName());
                    return bookHomeDto;
                }
        ).toList();
    }

    @Override
    public List<BookHomeDto> getBookByAuthorId(Integer authorId) {
        List<Book> books = bookRepo.findByAuthorId(authorId);
        return books.stream().map(
                (book) -> {
                    BookHomeDto bookHomeDto = modelMapper.map(book, BookHomeDto.class);
                    bookHomeDto.setAuthorName(book.getAuthor().getName());
                    return bookHomeDto;
                }
        ).toList();
    }

    @Override
    public List<BookHomeDto> searchBook(String keyword) {
        Set<Book> books = new HashSet<>();
        Set<Author> authors = new HashSet<>();
        Set<Category> categories = new HashSet<>();
        List<String>  words = new ArrayList<>();
        List<String> backup = new ArrayList<>();
        List<String> bannedWords = List.of("a", "an", "the", "and", "or", "but", "nor", "for", "so", "yet", "at", "around", "by", "after", "along", "for", "from", "of", "on", "to", "with", "without");
        for (String word: keyword.split("[.\\s-]")){
            if(bannedWords.contains(word.toLowerCase())){
                backup.add(word);
                continue;
            }
            words.add(word);
        }
        if(words.isEmpty()){
            words = backup;
        }
        words.forEach(
                (word) -> {
                    books.addAll(bookRepo.findByTitleContains(word));
                    authors.addAll(authorRepo.findByNameContains(word));
                    categories.addAll(categoryRepo.findByNameContains(word));
                }
        );

        authors.forEach(
                (author) -> {
                    books.addAll(author.getBooks());
                }
        );

        categories.forEach(
                (category) -> {
                    books.addAll(category.getBooks());
                }
        );

        return books.stream().map(
                (book) -> {
                    BookHomeDto bookHomeDto = modelMapper.map(book, BookHomeDto.class);
                    bookHomeDto.setAuthorName(book.getAuthor().getName());
                    return bookHomeDto;
                }
        ).toList();
    }
}
