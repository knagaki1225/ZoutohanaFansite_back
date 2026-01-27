package com.example.zoutohanafansite.mapper;

import com.example.zoutohanafansite.entity.form.ReviewForm;
import com.example.zoutohanafansite.entity.review.Review;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ReviewMapper {
    @Insert("""
        INSERT INTO Reviews (
            project_id,
            user_id,
            user_nickname,
            user_address,
            user_age_group,
            user_gender,
            user_self_introduction,
            book_isbn,
            book_title,
            book_publisher,
            book_author,
            review_title,
            review_content,
            draft
        ) VALUES (
            #{projectId},
            #{userId},
            #{userNickname},
            #{userAddress},
            #{userAgeGroup},
            #{userGender},
            #{userSelfIntroduction},
            #{bookIsbn},
            #{bookTitle},
            #{bookPublisher},
            #{bookAuthor},
            #{reviewTitle},
            #{reviewContent},
            #{draft}
        )
    """)
    @Options(useGeneratedKeys = true, keyProperty = "id,createdAt,updatedAt", keyColumn = "id,created_at,updated_at")
    void insertReview(Review review);

    @Select("SELECT * FROM reviews WHERE id = #{id} AND deleted = false")
    Review selectReviewById(long id);

    @Update("""
    UPDATE reviews
        SET
            project_id = #{projectId},
            user_id = #{userId},
            user_nickname = #{userNickname},
            user_address = #{userAddress},
            user_age_group = #{userAgeGroup},
            user_gender = #{userGender},
            user_self_introduction = #{userSelfIntroduction},
            book_isbn = #{bookIsbn},
            book_title = #{bookTitle},
            book_publisher = #{bookPublisher},
            book_author = #{bookAuthor},
            review_title = #{reviewTitle},
            review_content = #{reviewContent},
            draft = #{draft},
            updated_at = CURRENT_TIMESTAMP
    WHERE id = #{id};
    """)
    void updateDraftReview(Review review);

    @Update("UPDATE reviews SET deleted = true WHERE id = #{id}")
    void deleteReviewById(long id);

    @Select("SELECT * FROM reviews WHERE user_id = #{userId}")
    List<Review> selectReviewsByUserId(long userId);

    @Select("""
            SELECT id FROM reviews 
            WHERE project_id = #{projectId}
              AND user_id = #{userId}
              AND draft = true
              AND deleted = false
    """)
    Long selectDraftId(long projectId, long userId);

    @Select("""
            SELECT * FROM reviews
            WHERE user_id = #{userId}
              AND draft = false
              AND deleted = false
    """)
    List<Review> selectAllReviewsNotDraftByUserId(long userId);

    @Update("""
    UPDATE reviews
        SET
            review_title = #{reviewForm.reviewTitle},
            review_content = #{reviewForm.reviewContent},
            updated_at = CURRENT_TIMESTAMP
    WHERE id = #{id};
    """)
    void updateReview(ReviewForm reviewForm, long id);

    @Select("""
        SELECT r.*
        FROM reviews r 
        JOIN projects p ON r.project_id = p.id
        WHERE p.url_key = #{urlKey}
        AND r.first_stage_passed = true
    """)
    List<Review> selectReviewsByUrlKey(String urlKey);

    @Update("""
        UPDATE reviews
        SET vote_count = vote_count + 1
        WHERE id = #{id}
    """)
    void incrementVoteCount(long id);

    @Update("""
        UPDATE reviews
        SET vote_count = vote_count - 1
        WHERE id = #{id}
          AND vote_count > 0
    """)
    void decrementVoteCount(long id);
}
