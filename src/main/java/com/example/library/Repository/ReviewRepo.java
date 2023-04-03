package com.example.library.Repository;

import com.example.library.model.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepo extends JpaRepository<Review, Integer> {
}
