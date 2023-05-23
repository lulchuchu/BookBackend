package com.example.library.Repository;

import com.example.library.model.Entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorRepo extends JpaRepository<Author, Integer> {
    Optional<Author> findByName(String name);
}
