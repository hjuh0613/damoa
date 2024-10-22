package com.damoa.damoaPJT.heart.dto;

import com.damoa.damoaPJT.entity.Review;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MostHeartReviewResponse {

    private int reviewNo;

    private String reviewTitle;

    private String reviewContent;

    private LocalDateTime reviewDate;

    private int userNo;

    private String userNickname;

    @Builder
    public MostHeartReviewResponse(Review review) {
        this.reviewNo = review.getReviewNo();
        this.reviewTitle = review.getReviewTitle();
        this.reviewContent = review.getReviewContent();
        this.reviewDate = review.getReviewDate();
        this.userNo = review.getUser().getUserNo();
        this.userNickname = review.getUser().getUserNickname();
    }

}
