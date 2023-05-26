package com.example.library.Service;

import com.example.library.model.DTO.ReviewDto;
import com.example.library.model.Entity.User;

import java.util.List;

public interface ReviewService {
    List<ReviewDto> getReviewByBookId(int bookId);

    ReviewDto addReview(User user, ReviewDto reviewDto);
}
