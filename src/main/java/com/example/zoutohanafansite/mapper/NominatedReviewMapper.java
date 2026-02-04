package com.example.zoutohanafansite.mapper;

import com.example.zoutohanafansite.entity.nominatedreview.NominatedReview;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface NominatedReviewMapper {
    @Select("""
        SELECT * FROM nominated_reviews
        WHERE project_id = #{projectId}
            AND deleted = false
    """)
    List<NominatedReview> selectNominatedReviewByProjectId(long projectId);

    @Select("""
        SELECT nr.* FROM nominated_reviews nr
        JOIN projects p ON nr.project_id = p.id
        WHERE p.url_key = #{urlKey}
          AND nr.review_awarded = true
          AND nr.deleted = false;
    """)
    NominatedReview selectAwardReviewByUrlKey(String urlKey);

    @Select("""
        SELECT nr.* FROM nominated_reviews nr
        JOIN projects p ON nr.project_id = p.id
        WHERE p.url_key = #{urlKey}
          AND nr.review_awarded = false
          AND nr.deleted = false;
    """)
    List<NominatedReview> selectNotAwardReviewByUrlKey(String urlKey);
}
