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

    @Column(name = "report_type", nullable = false)
    private String reportType;

    @Column(name = "report_from_user_no", nullable = false) // 신고를 한 유저의 UserNo
    private int reportFromUserNo;

    @Column(name = "report_to_user_no", nullable = false) // 신고를 당한 유저의 UserNo
    private int reportToUserNo;

    @Column(name = "report_content", length = 900, nullable = false)
    private String reportContent;

    @Builder
    public Report(int reportNo, String reportType, int reportFromUserNo, int reportToUserNo, String reportContent) {
        this.reportNo = reportNo;
        this.reportType = reportType;
        this.reportFromUserNo = reportFromUserNo;
        this.reportToUserNo = reportToUserNo;
        this.reportContent = reportContent;
    }
}
