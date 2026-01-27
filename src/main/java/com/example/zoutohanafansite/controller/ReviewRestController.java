package com.example.zoutohanafansite.controller;

import com.example.zoutohanafansite.entity.review.ReviewApiData;
import com.example.zoutohanafansite.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewRestController {

    private final ReviewService reviewService;

    public ReviewRestController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{urlKey}")
    public ResponseEntity<List<ReviewApiData>> getReviews(@PathVariable String urlKey){
        List<ReviewApiData> reviews = reviewService.getReviewApiData(urlKey);
        return ResponseEntity.ok(reviews);
    }

    @PostMapping("/vote/{id}")
    public ResponseEntity<Void> voteReview(@PathVariable long id){
        reviewService.incrementVoteCount(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/vote/dec/{id}")
    public ResponseEntity<Void> voteReviewDec(@PathVariable long id){
        reviewService.incrementVoteCount(id);
        return ResponseEntity.ok().build();
    }
}
