package com.example.zoutohanafansite.entity.review;

import com.example.zoutohanafansite.entity.pagination.PaginationInfo;
import lombok.Data;

import java.util.List;

@Data
public class ReviewPagination {
//    エラー起きるので仮作成
    private PaginationInfo paginationInfo;
    private List<ReviewApiData> reviews;

    public ReviewPagination(PaginationInfo paginationInfo, List<ReviewApiData> reviews) {
        this.paginationInfo = paginationInfo;
        this.reviews = reviews;
    }
}
