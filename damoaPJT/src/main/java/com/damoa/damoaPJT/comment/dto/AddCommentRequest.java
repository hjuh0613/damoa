package com.damoa.damoaPJT.comment.dto;

import com.damoa.damoaPJT.entity.Comment;
import com.damoa.damoaPJT.entity.Review;
import com.damoa.damoaPJT.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
@AllArgsConstructor
public class AddCommentRequest {

    private int reviewNo;

    private String commentContent;

    private Integer parentCommentNo = null;

    private Integer userNo = null;

    public Comment toEntity() {
        return Comment.builder()
                .commentContent(commentContent)
                .parentCommentNo(parentCommentNo)
                .commentDate(LocalDateTime.now())
                .review(Review.builder()
                        .reviewNo(reviewNo)
                        .build())
                .user(User.builder()
                        .userNo(userNo)
                        .build())
                .build();
    }

    public void setUserNo(int userNo){
        this.userNo = userNo;
    }

}
