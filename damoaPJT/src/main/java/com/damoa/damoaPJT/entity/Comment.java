package com.damoa.damoaPJT.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
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
    @Column(name = "comment_date", nullable = false)
    private LocalDateTime commentDate;

    @Column(name = "group_no", nullable = false)
    private int groupNo;

    @Column(name = "group_order", nullable = false)
    private int groupOrder;

    @Column(name = "group_depth", nullable = false)
    private int groupDepth;

    @ManyToOne
    @JoinColumn(name = "review_no")
    private Review review;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;
}