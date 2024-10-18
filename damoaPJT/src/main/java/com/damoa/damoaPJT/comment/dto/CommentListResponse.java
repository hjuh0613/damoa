package com.damoa.damoaPJT.comment.dto;

import com.damoa.damoaPJT.entity.Comment;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentListResponse {

    private int commentNo;

    private String commentContent;

    private LocalDateTime commentDate;

    private Integer parentCommentNo = null;

    private int reviewNo;

    private int userNo;

    private String userNickname;

    @Builder
    public CommentListResponse(Comment comment) {
        this.commentNo  = comment.getCommentNo();
        this.commentContent = comment.getCommentContent();
        this.commentDate = comment.getCommentDate();
        this.parentCommentNo = comment.getParentCommentNo();
        this.reviewNo = comment.getReview().getReviewNo();
        this.userNo = comment.getUser().getUserNo();
        this.userNickname = comment.getUser().getUserNickname();
    }
}
