package com.damoa.damoaPJT.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity // 엔티티 등록
@Getter // getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_no", nullable = false)
    private int commentNo;

    @Column(name = "comment_content", length = 300, nullable = false)
    private String commentContent;

    @CreatedDate
    @Column(name = "comment_date")
    private LocalDateTime commentDate;

    @Column(name = "parent_comment_no", nullable = true)
    private Integer parentCommentNo;

    @ManyToOne
    @JoinColumn(name = "review_no")
    private Review review;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

    @Builder
    public Comment(int commentNo, String commentContent, LocalDateTime commentDate, Integer parentCommentNo, Review review, User user) {
        this.commentNo = commentNo;
        this.commentContent = commentContent;
        this.commentDate = commentDate;
        this.parentCommentNo = parentCommentNo;
        this.review = review;
        this.user = user;
    }

}
