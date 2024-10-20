package com.damoa.damoaPJT.comment.dto;

import com.damoa.damoaPJT.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateCommentRequest {

    private int commentNo;

    private String commentContent;

    public Comment toEntity() {
        return Comment.builder()
                .commentNo(commentNo)
                .commentContent(commentContent)
                .build();
    }
}
