package com.damoa.damoaPJT.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // 엔티티 등록
@Getter // getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1씩 자동 증가
    @Column(name = "report_No", nullable = false)
    private int reportNo;

    @Column(name = "report_content", length = 900, nullable = false)
    private String reportContent;

    @ManyToOne
    @JoinColumn(name = "report_from_user_no")   // 신고를 한 유저의 UserNo
    private User reportFromUser;

    @ManyToOne
    @JoinColumn(name = "report_to_user_no")   // 신고를 당한 유저의 User
    private User reportToUser;

    @Column(name="board_no")
    private int boardNo;    // 어떤 판매글을 신고 했는지

    @Column(name = "board_type_no", nullable = false)
    private int boardTypeNo;  // 어떤 카테고리의 글을 신고 했는지

    @Builder
    public Report(int reportNo, String reportContent, User reportFromUser, User reportToUser, int boardNo, int boardTypeNo) {
        this.reportNo = reportNo;
        this.reportFromUser = reportFromUser;
        this.reportToUser = reportToUser;
        this.boardNo = boardNo;
        this.reportContent = reportContent;
        this.boardTypeNo = boardTypeNo;
    }
}
