package com.damoa.damoaPJT.userReview.dto;

import com.damoa.damoaPJT.entity.Review;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserReviewUpdateRequest {

    private int reviewNo;

    private String reviewTitle;

    private String reviewContent;

    public Review toEntity(){
        return Review.builder()
                .reviewNo(reviewNo)
                .reviewTitle(reviewTitle)
                .reviewContent(reviewContent)
                .build();
    }

}
