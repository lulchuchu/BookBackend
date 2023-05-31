package com.example.library.Controller;

import com.example.library.Service.BookService;
import com.example.library.Service.CategoryService;
import com.example.library.model.DTO.BookDTO;
import com.example.library.model.DTO.BookHomeDto;
import com.example.library.model.DTO.FilterDTO;
import com.example.library.model.Entity.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Filter;

@RestController
@RequestMapping("/api/book")
@CrossOrigin
public class BookController {

    private final BookService bookService;
    private final CategoryService categoryService;

    public BookController(BookService bookService, CategoryService categoryService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

//    @GetMapping("/all")
//    public ResponseEntity<?> getAllBooksHome() {
//        return ResponseEntity.ok().body(bookService.getAllBooksHome());
//    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBooksHome(FilterDTO filterDto) {
        return ResponseEntity.ok().body(bookService.getAllBooksHome(filterDto));
  }

    @GetMapping("/all/bestseller")
    public ResponseEntity<?> getAllBooksHomeBestSeller() {
        return ResponseEntity.ok().body(bookService.getAllBooksHomeBestSeller());
    }

    @GetMapping("/all/new")
    public ResponseEntity<?> getAllBooksHomeNew() {
        return ResponseEntity.ok().body(bookService.getAllBooksHomeNew());
    }

    @GetMapping("/all/priceIncrease")
    public ResponseEntity<?> getAllBooksHomePriceIncrease() {
        return ResponseEntity.ok().body(bookService.getAllBooksHomePriceIncrease());
    }

    @GetMapping("/all/priceDecrease")
    public ResponseEntity<?> getAllBooksHomePriceDecrease() {
        return ResponseEntity.ok().body(bookService.getAllBooksHomePriceDecrease());
    }

    @GetMapping("/allbooks")
    public ResponseEntity<?> getAllBooks() {
        return ResponseEntity.ok().body(bookService.getAllBooks());
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteBook(@RequestParam Integer bookId) {
        bookService.deleteBook(bookId);
        return ResponseEntity.ok().body("Delete book successfully");
    }

//    @GetMapping("/search")
//    public ResponseEntity<?> searchBook(@RequestParam String keyword) {
//        List<BookHomeDto> books= bookService.searchBook(keyword);
//        return ResponseEntity.ok().body(books);
//    }

    @GetMapping("/category")
    public ResponseEntity<?> getBooksByCategory(@RequestParam Integer categoryId) {
        List<BookHomeDto> books= bookService.getBookByCategoryId(categoryId);
        return ResponseEntity.ok().body(books);
    }

    @GetMapping("/author")
    public ResponseEntity<?> getBooksByAuthor(@RequestParam Integer authorId) {
        List<BookHomeDto> books= bookService.getBookByAuthorId(authorId);
        return ResponseEntity.ok().body(books);
    }

    @GetMapping("/details")
    public ResponseEntity<Book> getBookDetails(@RequestParam Integer bookId) {
        return ResponseEntity.ok().body(bookService.getBookDetails(bookId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody BookDTO book) {
        bookService.addBook(book);
        return ResponseEntity.ok().body("Book added successful");
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateBook(@RequestBody BookDTO book) {
        bookService.updateBook(book);
        return ResponseEntity.ok().body("Book updated successful");
    }
}
