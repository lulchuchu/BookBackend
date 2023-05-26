package com.example.library.Service.impl;

import com.example.library.Repository.BookRepo;
import com.example.library.Repository.ReviewRepo;
import com.example.library.Service.ReviewService;
import com.example.library.model.DTO.ReviewDto;
import com.example.library.model.Entity.Review;
import com.example.library.model.Entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static java.sql.Types.NULL;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ReviewRepo reviewRepo;
    private final ModelMapper modelMapper;
    private final BookRepo bookRepo;

    public ReviewServiceImpl(ReviewRepo reviewRepo, ModelMapper modelMapper, BookRepo bookRepo) {
        this.reviewRepo = reviewRepo;
        this.modelMapper = modelMapper;
        this.bookRepo = bookRepo;
    }

    @Override
    public List<ReviewDto> getReviewByBookId(int bookId) {
        List<Review> reviews = reviewRepo.findByBookId(bookId);
        return reviews.stream().map((review -> {
            ReviewDto reviewDto = modelMapper.map(review, ReviewDto.class);
            reviewDto.setBookId(review.getBook().getId());
            reviewDto.setBookName(review.getBook().getTitle());
            reviewDto.setUserId(review.getUser().getId());
            reviewDto.setUserName(review.getUser().getUsername());
            return  reviewDto;
        })).toList();
    }

    @Override
    public ReviewDto addReview(User user, ReviewDto reviewDto) {
        Review review = modelMapper.map(reviewDto, Review.class);
        review.setBook(bookRepo.findById(reviewDto.getBookId()).orElseThrow());
        review.setUser(user);
        review.setDate(LocalDateTime.now());
        reviewRepo.save(review);
        return reviewDto;
    }
}
