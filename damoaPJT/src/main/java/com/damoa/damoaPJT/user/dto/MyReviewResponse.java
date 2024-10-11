package com.damoa.damoaPJT.user.dto;

import com.damoa.damoaPJT.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyReviewResponse {

    private int reviewNo;

    private String reviewTitle;

    private LocalDateTime reviewDate;

    @Builder
    public MyReviewResponse(Review review) {
        this.reviewNo = review.getReviewNo();
        this.reviewTitle = review.getReviewTitle();
        this.reviewDate = review.getReviewDate();
    }
}
