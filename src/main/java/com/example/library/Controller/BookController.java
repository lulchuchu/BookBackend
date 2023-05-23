package com.example.library.Controller;

import com.example.library.Service.BookService;
import com.example.library.Service.CategoryService;
import com.example.library.model.DTO.BookDTO;
import com.example.library.model.Entity.Book;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/book")
public class BookController {

    private final BookService bookService;
    private final CategoryService categoryService;

    public BookController(BookService bookService, CategoryService categoryService) {
        this.bookService = bookService;
        this.categoryService = categoryService;
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllBooks() {
        return ResponseEntity.ok().body(bookService.getAllBooks());
    }

    @GetMapping("/details")
    public ResponseEntity<Book> getBookDetails(Integer bookId) {
        return ResponseEntity.ok().body(bookService.getBookDetails(bookId));
    }

    @PostMapping("/add")
    public ResponseEntity<?> addBook(@RequestBody BookDTO book) {
        bookService.addBook(book);
        return ResponseEntity.ok().body("Book added successful");
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateBook(@RequestBody BookDTO book) {
        bookService.updateBook(book);
        return ResponseEntity.ok().body("Book updated successful");
    }

    @GetMapping("/category")
    public ResponseEntity<?> getBooksByCategory(@RequestParam Integer categoryId) {
        List<Book> books= categoryService.showBooksByCategory(categoryId);
        return ResponseEntity.ok().body(books);
    }
}
