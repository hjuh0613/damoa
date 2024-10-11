package com.damoa.damoaPJT.userReview.dto;

import com.damoa.damoaPJT.entity.Review;
import com.damoa.damoaPJT.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewAddRequest {

    private String reviewTitle;

    private String reviewContent;

    private Integer userNo = null;

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

    public void setUserNo(int userNo){
        this.userNo = userNo;
    }

}
