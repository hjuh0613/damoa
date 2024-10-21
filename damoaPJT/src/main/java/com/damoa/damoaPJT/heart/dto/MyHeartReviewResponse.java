package com.damoa.damoaPJT.heart.dto;

import com.damoa.damoaPJT.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyHeartReviewResponse {

    private int reviewNo;

    private String reviewTitle;

    private LocalDateTime reviewDate;

    @Builder
    public MyHeartReviewResponse(Review review) {
        this.reviewNo = review.getReviewNo();
        this.reviewTitle = review.getReviewTitle();
        this.reviewDate = review.getReviewDate();
    }
}
