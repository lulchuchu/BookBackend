package com.example.library.Repository;

import com.example.library.model.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDate;

public interface BookRepo extends JpaRepository<Book, Integer> {
}
