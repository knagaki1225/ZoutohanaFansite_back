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

    /**
     * 新規review登録
     *
     * @param review 新規登録するreview
     */
    public void setReview(Review review) {
        reviewRepository.insertReview(review);
    }

    /**
     * reviewIdを指定してreview取得
     *
     * @param id 指定するreviewId
     * @return Review
     */
    public Review getReviewById(long id){
        return reviewRepository.selectReviewById(id);
    }

    /**
     * 下書き保存したreviewの更新
     * 下書き保存のまま更新の場合はproject.draftをtrue
     *
     * @param review 更新するreview
     */
    public void updateDraftReview(Review review){
        reviewRepository.updateDraftReview(review);
    }

    /**
     * reviewを論理削除
     *
     * @param id 削除するreviewId
     */
    public void deleteReviewById(long id){
        reviewRepository.deleteReviewById(id);
    }

    /**
     * 指定したuserのreviewを全件取得
     *
     * @param userId 指定するuserId
     * @return List<Review>
     **/
    public List<Review> getReviewByUserId(long userId){
        return reviewRepository.selectReviewByUserId(userId);
    }

    /**
     * List<Review>の中に下書きのものがあるかどうか
     *
     * @param reviews 検索するreview
     * @return long 下書きのものがある場合そのreviewId、ない場合-1
     */
    public long isDraftReview(List<Review> reviews){
        for(Review review : reviews){
            if(review.isDraft()){
                return review.getId();
            }
        }
        return -1;
    }

    /**
     * projectIdとuserIdを指定して下書きのreviewIdを取得
     *
     * @param projectId
     * @param userId
     * @return Long ない場合はnull
     */
    public Long getDraftId(long projectId, long userId){
        return reviewRepository.selectDraftId(projectId, userId);
    }

    /**
     * userIdを取得して下書きではないreviewを全件取得
     *
     * @param userId
     * @return List<Review>
     */
    public List<Review> getAllReviewsNotDraftByUserId(long userId){
        return reviewRepository.selectAllReviewsNotDraftByUserId(userId);
    }

    /**
     * reviewの編集(投稿後)
     *
     * @param reviewForm
     */
    public void editReview(ReviewForm reviewForm, long id){
        reviewRepository.updateReview(reviewForm, id);
    }

    /**
     * 指定したurlKeyのprojectに属するreviewを全件取得
     *
     * @param urlKey 指定するurlKey
     * @return List<Review>
     */
    public List<Review> selectReviewsByUrlKey(String urlKey){
        return reviewRepository.selectReviewsByUrlKey(urlKey);
    }

    /**
     * 指定したurlKeyのprojectに属するreviewを全件取得(api用データに変換)
     *
     * @param urlKey 指定するurlKey
     * @return <ReviewApiData>
     */
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

    /**
     * 指定したurlKeyのprojectに属するreviewのページネーション用データ取得
     *
     * @param urlKey
     * @param page 取得するページ数
     * @return ReviewPagination
     */
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

    /**
     * 指定したidのreviewのvoteCountを1プラス
     *
     * @param id
     */
    public void incrementVoteCount(long id){
        reviewRepository.incrementVoteCount(id);
    }

    /**
     * 指定したidのreviewのvoteCountを1マイナス
     *
     * @param id
     */
    public void decrementVoteCount(long id){
        reviewRepository.decrementVoteCount(id);
    }

    /**
     * 指定したurlKeyのprojectに属するreviewの中に指定したidのListの中に含まれる場合Reviewを返す
     *
     * @param urlKey
     * @param idList
     * @return Review
     */
    public Review selectReviewByUrlKeyAndIdList(String urlKey, List<String> idList){
        List<Long> ids = new ArrayList<>();
        for(String id : idList){
            ids.add(Long.parseLong(id));
        }
        return reviewRepository.selectReviewByUrlKeyAndIdList(urlKey, ids);
    }
}
