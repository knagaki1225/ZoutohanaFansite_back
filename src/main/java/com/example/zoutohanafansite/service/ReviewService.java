package com.example.zoutohanafansite.service;

import com.example.zoutohanafansite.entity.form.ReviewForm;
import com.example.zoutohanafansite.entity.review.Review;
import com.example.zoutohanafansite.entity.review.ReviewApiData;
import com.example.zoutohanafansite.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void setReview(Review review) {
        reviewRepository.insertReview(review);
    }

    public Review getReviewById(long id){
        return reviewRepository.selectReviewById(id);
    }

    public void updateDraftReview(Review review){
        reviewRepository.updateDraftReview(review);
    }

    public void deleteReviewById(long id){
        reviewRepository.deleteReviewById(id);
    }

    public List<Review> getReviewByUserId(long userId){
        return reviewRepository.selectReviewByUserId(userId);
    }

    public long isDraftReview(List<Review> reviews){
        for(Review review : reviews){
            if(review.isDraft()){
                return review.getId();
            }
        }
        return -1;
    }

    public Long getDraftId(long projectId, long userId){
        return reviewRepository.selectDraftId(projectId, userId);
    }

    public List<Review> getAllReviewsNotDraftByUserId(long userId){
        return reviewRepository.selectAllReviewsNotDraftByUserId(userId);
    }

    public void editReview(ReviewForm reviewForm, long id){
        reviewRepository.updateReview(reviewForm, id);
    }

    public List<Review> selectReviewsByUrlKey(String urlKey){
        return reviewRepository.selectReviewsByUrlKey(urlKey);
    }

    public List<ReviewApiData> getReviewApiData(String urlKey){
        List<Review> reviews = selectReviewsByUrlKey(urlKey);
        List<ReviewApiData> reviewApiDataList = new ArrayList<>();
        int i = 1;
        for(Review review : reviews){
            ReviewApiData reviewApiData = new ReviewApiData(review, "/api/image/book" + i + ".png");
            reviewApiDataList.add(reviewApiData);
            if(i == 4){
                i = 1;
            }else{
                i++;
            }
        }

        Collections.shuffle(reviewApiDataList);
        return reviewApiDataList;
    }

    public void incrementVoteCount(long id){
        reviewRepository.incrementVoteCount(id);
    }

    public void decrementVoteCount(long id){
        reviewRepository.decrementVoteCount(id);
    }
}
