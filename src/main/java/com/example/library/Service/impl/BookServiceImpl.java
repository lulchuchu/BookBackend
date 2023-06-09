package com.example.library.Service.impl;

import com.example.library.Exception.AlreadyExistException;
import com.example.library.Exception.NotFoundException;
import com.example.library.Repository.AuthorRepo;
import com.example.library.Repository.BookRepo;
import com.example.library.Repository.CategoryRepo;
import com.example.library.Service.BookService;
import com.example.library.model.DTO.BookDTO;
import com.example.library.model.DTO.BookHomeDto;
import com.example.library.model.DTO.FilterDTO;
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

//    @Override
//    public List<BookHomeDto> getAllBooksHome() {
//        List<Book> books = bookRepo.findAll();
//        if (books.isEmpty()) {
//            throw new NotFoundException("No books found");
//        }
//        return books.stream().map(
//                (book) -> {
//                    BookHomeDto bookHomeDto = modelMapper.map(book, BookHomeDto.class);
//                    bookHomeDto.setAuthorName(book.getAuthor().getName());
//                    return bookHomeDto;
//                }
//        ).toList();
//    }

    @Override
    public List<BookHomeDto> getAllBooksHome(FilterDTO filter) {
        List<Book> books;
        if (filter.getKeyword() != null) {
            books = searchBook(filter.getKeyword());
        }else{
            books = bookRepo.findAll();
        }
        if (books.isEmpty()) {
            throw new NotFoundException("No books found");
        }

        if(filter.getAuthorId() != null){
            Optional<Author> author = authorRepo.findById(filter.getAuthorId());
            if(author.isPresent()){
                books.removeIf(book -> !book.getAuthor().equals(author.get()));
            }
        }

        if(filter.getCategoryId() != null){
            Optional<Category> category = categoryRepo.findById(filter.getCategoryId());
            if(category.isPresent()){
                books.removeIf(book -> !book.getCategories().contains(category.get()));
            }
        }

        List<BookHomeDto> bookHomeDtos = new ArrayList<>();
        for (Book book : books) {
            BookHomeDto bookHomeDto = modelMapper.map(book, BookHomeDto.class);
            bookHomeDto.setAuthorName(book.getAuthor().getName());
            bookHomeDtos.add(bookHomeDto);
        }
        if (filter.getSort() != null) {
            if (filter.getSort().equals("bestseller")) {
                bookHomeDtos.sort(Comparator.comparing(BookHomeDto::getSold).reversed());
            } else if (filter.getSort().equals("new")) {
                bookHomeDtos.sort(Comparator.comparing(BookHomeDto::getReleaseDate).reversed());
            } else if (filter.getSort().equals("priceIncrease")) {
                bookHomeDtos.sort(Comparator.comparing(BookHomeDto::getPrice));
            } else if (filter.getSort().equals("priceDecrease")) {
                bookHomeDtos.sort(Comparator.comparing(BookHomeDto::getPrice).reversed());
            }
        }
        return bookHomeDtos;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepo.findAll();
        if (books.isEmpty()) {
            throw new NotFoundException("No books found");
        }
        return books.stream().map(
                (book) -> {
                    BookDTO bookDto = modelMapper.map(book, BookDTO.class);
                    bookDto.setCategories(book.getCategories());
                    bookDto.setAuthorName(book.getAuthor().getName());
                    return bookDto;
                }
        ).toList();
    }

    @Override
    public List<BookHomeDto> getAllBooksHomeBestSeller() {
        List<Book> books = bookRepo.findByOrderBySoldDesc();
        if (books.isEmpty()) {
            throw new NotFoundException("No books found");
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
    public List<BookHomeDto> getAllBooksHomeNew() {
        List<Book> books = bookRepo.findByOrderByReleaseDateDesc();
        if (books.isEmpty()) {
            throw new NotFoundException("No books found");
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
    public List<BookHomeDto> getAllBooksHomePriceIncrease() {
        List<Book> books = bookRepo.findByOrderByPriceAsc();
        if (books.isEmpty()) {
            throw new NotFoundException("No books found");
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
    public List<BookHomeDto> getAllBooksHomePriceDecrease() {
        List<Book> books = bookRepo.findByOrderByPriceDesc();
        if (books.isEmpty()) {
            throw new NotFoundException("No books found");
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
            throw new NotFoundException("Book not found");
        }
        return book.get();
    }

    @Override
    public Book addBook(BookDTO bookDTO) {
        if(bookRepo.findByTitleAndAuthorName(bookDTO.getTitle(), bookDTO.getAuthorName()) != null){
            throw new AlreadyExistException("Book already exists");
        }

        Optional<Author> author = authorRepo.findByNameIgnoreCase(bookDTO.getAuthorName());

        if (author.isEmpty()) {
            Author newAuthor = new Author();
            newAuthor.setName(bookDTO.getAuthorName());
            authorRepo.save(newAuthor);
            author = Optional.of(newAuthor);
        }

        List<Category> categories = bookDTO.getCategories();

        Book book = new Book(bookDTO.getTitle(), bookDTO.getCover(), bookDTO.getDescription(), bookDTO.getReleaseDate(), bookDTO.getPages(), bookDTO.getPrice(),author.get(), bookDTO.getQuantity(), categories);
        bookRepo.save(book);
        return book;
    }

    @Override
    public Book updateBook(BookDTO bookDTO) {
        Optional<Author> author = authorRepo.findByNameIgnoreCase(bookDTO.getAuthorName());
        if (author.isEmpty()) {
            Author newAuthor = new Author();
            newAuthor.setName(bookDTO.getAuthorName());
            authorRepo.save(newAuthor);
            author = Optional.of(newAuthor);
        }

        List<Category> categories = bookDTO.getCategories();

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
    public String deleteBook(Integer bookId) {
        bookRepo.deleteById(bookId);
        return "Delete successfully";
    }

    @Override
    public List<Book> getBooksByCategory(Integer categoryName) {
        Optional<Category> category = categoryRepo.findById(categoryName);
        if (category.isEmpty()) {
            throw new NotFoundException("Category not found");
        }
        return category.get().getBooks();
    }

    @Override
    public List<Book> getBooksByAuthor(Integer authorId) {
        Optional<Author> author = authorRepo.findById(authorId);
        if (author.isEmpty()) {
            throw new NotFoundException("Author not found");
        }
        return author.get().getBooks();
    }

    @Override
    public List<BookHomeDto> getBookByCategoryId(Integer categoryId) {
        Optional<Category> category = categoryRepo.findById(categoryId);
        if (category.isEmpty()) {
            throw new NotFoundException("Category not found");
        }
        return category.get().getBooks().stream().map(
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

//    @Override
    public List<Book> searchBook(String keyword) {
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
                    authors.addAll(authorRepo.findByNameContainsIgnoreCase(word));
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

        return new ArrayList<>(books);
    }


}
