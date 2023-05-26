package com.example.library.Repository;

import com.example.library.model.Entity.Author;
import com.example.library.model.Entity.Book;
import com.example.library.model.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

public interface BookRepo extends JpaRepository<Book, Integer> {
    List<Book> findByAuthorId(Integer authorId);
//    List<Book> findByCategoriesContains(Category category);
}
