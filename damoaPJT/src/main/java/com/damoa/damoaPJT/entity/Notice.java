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
@Table(name = "notice")
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1씩 자동 증가
    @Column(name = "notice_no", nullable = false)
    private int noticeNo;

    @Column(name = "notice_category", length = 45, nullable = false)
    private String noticeCategory;

    @Column(name = "notice_title", length = 150, nullable = false)
    private String noticeTitle;

    @Column(name = "notice_content", length = 1500, nullable = false)
    private String noticeContent;

    @CreatedDate
    @Column(name = "notice_date", nullable = false)
    private LocalDateTime noticeDate;
}
