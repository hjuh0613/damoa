package com.damoa.damoaPJT.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity // 엔티티 등록
@Getter // getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자
@Table(name = "review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1씩 자동 증가
    @Column(name = "review_no", nullable = false)
    private int reviewNo;

    @Column(name = "review_title", length = 150, nullable = false)
    private String reviewTitle;

    @Column(name = "review_content", length = 1500, nullable = false)
    private String reviewContent;

    @Column(name = "review_date", nullable = false)
    private LocalDateTime reviewDate;

    @OneToMany(mappedBy = "review")
    private List<Comment> comments = new ArrayList<Comment>();

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

    @Builder
    public Review(int reviewNo, String reviewTitle, String reviewContent, LocalDateTime reviewDate, List<Comment> comments, User user){
        this.reviewNo = reviewNo;
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
        this.reviewDate = reviewDate;
        this.comments = comments;
        this.user = user;
    }

    public void update(String reviewTitle, String reviewContent){
        this.reviewTitle = reviewTitle;
        this.reviewContent = reviewContent;
    }
}
