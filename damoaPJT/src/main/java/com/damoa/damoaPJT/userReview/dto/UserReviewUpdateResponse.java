package com.damoa.damoaPJT.userReview.dto;

import com.damoa.damoaPJT.entity.Review;
import lombok.Getter;

@Getter
public class UserReviewUpdateResponse {

    private int reviewNo;

    private String reviewTitle;

    private String reviewContent;

    public UserReviewUpdateResponse(Review entity) {
        this.reviewNo = entity.getReviewNo();
        this.reviewTitle = entity.getReviewTitle();
        this.reviewContent = entity.getReviewContent();
    }
}
