package com.damoa.damoaPJT.report.dto;

import com.damoa.damoaPJT.entity.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReportListResponse {

    private int reportNo;

    private int boardTypeNo;

    private int reportFromUserNo;

    private int reportToUserNo;

    private String reportContent;

    @Builder
    public ReportListResponse(Report report) {
        this.reportNo = report.getReportNo();
        this.boardTypeNo = report.getBoardTypeNo();
        this.reportFromUserNo = report.getReportFromUser().getUserNo();
        this.reportToUserNo = report.getReportToUser().getUserNo();
        this.reportContent = report.getReportContent();
    }

}
