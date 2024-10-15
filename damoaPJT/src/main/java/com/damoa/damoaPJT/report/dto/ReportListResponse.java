package com.damoa.damoaPJT.report.dto;

import com.damoa.damoaPJT.entity.Report;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReportListResponse {

    private int reportNo;

    private String reportType;

    private int reportFromUserNo;

    private int reportToUserNo;

    private String reportContent;

    @Builder
    public ReportListResponse(Report report) {
        this.reportNo = report.getReportNo();
        this.reportType = report.getReportType();
        this.reportFromUserNo = report.getReportFromUserNo();
        this.reportToUserNo = report.getReportToUserNo();
        this.reportContent = report.getReportContent();
    }

}
