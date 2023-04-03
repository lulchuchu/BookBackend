package com.example.library.Repository;

import com.example.library.model.Entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepo extends JpaRepository<Author, Integer> {
}
