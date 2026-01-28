package com.example.zoutohanafansite.service;

import com.example.zoutohanafansite.entity.form.ReviewForm;
import com.example.zoutohanafansite.entity.pagination.PaginationView;
import com.example.zoutohanafansite.entity.project.Project;
import com.example.zoutohanafansite.entity.review.Review;
import com.example.zoutohanafansite.entity.review.ReviewApiData;
import com.example.zoutohanafansite.entity.review.ReviewPagination;
import com.example.zoutohanafansite.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final PaginationService paginationService;
    private final ProjectService projectService;

    public ReviewService(ReviewRepository reviewRepository, PaginationService paginationService, ProjectService projectService) {
        this.reviewRepository = reviewRepository;
        this.paginationService = paginationService;
        this.projectService = projectService;
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

    public ReviewPagination getReviewApiData(String urlKey, int page){
        List<Review> reviews = selectReviewsByUrlKey(urlKey);
        List<ReviewApiData> reviewApiDataList = new ArrayList<>();

        PaginationView paginationView = paginationService.getPaginationView(page, reviews.size(), 10);
        for(int i = paginationView.getStartNum(); i < paginationView.getEndNum(); i++){
            ReviewApiData reviewApiData = new ReviewApiData(reviews.get(i), "/api/image/book" + (i % 4 + 1) + ".png");
            reviewApiDataList.add(reviewApiData);
        }

        return new ReviewPagination(paginationView.getPaginationInfo(), reviewApiDataList);
    }

    public void incrementVoteCount(long id){
        reviewRepository.incrementVoteCount(id);
    }

    public void decrementVoteCount(long id){
        reviewRepository.decrementVoteCount(id);
    }

    public Review selectReviewByUrlKeyAndIdList(String urlKey, List<String> idList){
        List<Long> ids = new ArrayList<>();
        for(String id : idList){
            ids.add(Long.parseLong(id));
        }
        return reviewRepository.selectReviewByUrlKeyAndIdList(urlKey, ids);
    }
}
