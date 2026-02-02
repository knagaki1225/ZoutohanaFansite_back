package com.example.zoutohanafansite.controller;

import com.example.zoutohanafansite.entity.review.Review;
import com.example.zoutohanafansite.entity.review.ReviewApiData;
import com.example.zoutohanafansite.entity.review.ReviewPagination;
import com.example.zoutohanafansite.service.ProjectService;
import com.example.zoutohanafansite.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewRestController {

    private final ReviewService reviewService;
    private final ProjectService projectService;

    public ReviewRestController(ReviewService reviewService, ProjectService projectService) {
        this.reviewService = reviewService;
        this.projectService = projectService;
    }

    @GetMapping("/{urlKey}")
    public ResponseEntity<List<ReviewApiData>> getReviews(@PathVariable String urlKey){
        List<ReviewApiData> reviews = reviewService.getReviewApiData(urlKey);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/list/{urlKey}")
    public ResponseEntity<ReviewPagination> getReviewList(@PathVariable String urlKey, @RequestParam(defaultValue = "1") int page){
        return ResponseEntity.ok(reviewService.getReviewApiData(urlKey, page));
    }

    @PostMapping("/vote/{id}")
    public ResponseEntity<Void> voteReview(@PathVariable long id){
        reviewService.incrementVoteCount(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/vote/dec/{id}")
    public ResponseEntity<Void> voteReviewDec(@PathVariable long id){
        reviewService.decrementVoteCount(id);
        return ResponseEntity.ok().build();
    }

    // 投票済みかどうかを返す
    @PostMapping("/voted/{urlKey}")
    public ResponseEntity<ReviewApiData> getVotedReview(@PathVariable String urlKey, @RequestBody List<String> idList){
        List<Review> review = reviewService.selectReviewByUrlKeyAndIdList(urlKey, idList);
        if(review == null){
            return ResponseEntity.notFound().build();
        }
        ReviewApiData reviewApiData = new ReviewApiData(review.getFirst(), "");
        return ResponseEntity.ok(reviewApiData);
    }

    @PostMapping("/favorite/{urlKey}")
    public ResponseEntity<List<ReviewApiData>> getFavoriteReview(@PathVariable String urlKey, @RequestBody List<String> idList){
        List<Review> reviews = reviewService.selectReviewByUrlKeyAndIdList(urlKey, idList);
        if(reviews == null){
            return ResponseEntity.notFound().build();
        }
        LocalDateTime votingEndAt = projectService.getVotingEndAt(urlKey);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime oneWeekLater = now.plusWeeks(1);
        boolean isVoteCount = votingEndAt.isAfter(now) && votingEndAt.isBefore(oneWeekLater.plusNanos(1));
        List<ReviewApiData> reviewApiDataList = new ArrayList<>();
        int i = 0;
        for(Review review : reviews){
            ReviewApiData reviewApiData = new ReviewApiData(reviews.get(i), "/api/image/book" + (i % 4 + 1) + ".png");
            if(isVoteCount){
                reviewApiData.setVoteCount(null);
            }
            reviewApiDataList.add(reviewApiData);
            i++;
        }
        return ResponseEntity.ok(reviewApiDataList);
    }
}