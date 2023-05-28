package com.example.library.Repository;

import com.example.library.model.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface BookRepo extends JpaRepository<Book, Integer> {
    List<Book> findByAuthorId(Integer authorId);
    Set<Book> findByTitleContains(String keyword);

    List<Book> findByOrderBySoldDesc();
    List<Book> findByOrderByReleaseDateDesc();
    List<Book> findByOrderByPriceAsc();
    List<Book> findByOrderByPriceDesc();
}
