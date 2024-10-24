package com.damoa.damoaPJT.report.dto;

import com.damoa.damoaPJT.entity.Board;
import com.damoa.damoaPJT.entity.Report;
import com.damoa.damoaPJT.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class ReportListDetailResponse {

    private int reportNo;
    private String reportContent;
    private User reportFromUser;
    private User reportToUser;
    private int boardNo;
    private int boardTypeNo;

    @Builder
    public ReportListDetailResponse(Report report) {
        this.reportNo = report.getReportNo();
        this.reportContent = report.getReportContent();
        this.reportFromUser = report.getReportFromUser();
        this.reportToUser = report.getReportToUser();
        this.boardNo = report.getBoardNo();
        this.boardTypeNo = report.getBoardTypeNo();
    }

}