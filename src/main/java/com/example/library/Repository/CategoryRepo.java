package com.example.library.Repository;

import com.example.library.model.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
    Optional<Category> findByName(String name);
    List<Category> findByNameContains(String keyword);
}
