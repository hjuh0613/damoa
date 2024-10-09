package com.damoa.damoaPJT.userReview.dto;

import com.damoa.damoaPJT.entity.Review;
import com.damoa.damoaPJT.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewAddRequest {

    private String reviewTitle;

    private String reviewContent;

    private int userNo;

    public Review toEntity() {
        return Review.builder()
                .reviewTitle(reviewTitle)
                .reviewContent(reviewContent)
                .reviewDate(LocalDateTime.now())
                .user(User.builder()
                        .userNo(userNo)
                        .build())
                .build();
    }
}
