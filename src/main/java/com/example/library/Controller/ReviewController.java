package com.example.library.Controller;

import com.example.library.Security.UserDetail;
import com.example.library.Service.ReviewService;
import com.example.library.model.DTO.ReviewDto;
import com.example.library.model.Entity.Review;
import com.example.library.model.Entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
@CrossOrigin
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/book")
    public ResponseEntity<?> getReviewByBookId(@RequestParam int bookId) {
        List<ReviewDto> review = reviewService.getReviewByBookId(bookId);
        return ResponseEntity.ok(review);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addReview(@RequestBody ReviewDto reviewDto, Authentication authentication) {
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        User user = userDetail.getUser();
        ReviewDto reviewDto1 = reviewService.addReview(user, reviewDto);
        return ResponseEntity.ok(reviewDto1);
    }
}
