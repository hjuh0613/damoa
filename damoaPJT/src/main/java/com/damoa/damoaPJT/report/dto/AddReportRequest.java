package com.damoa.damoaPJT.report.dto;

import com.damoa.damoaPJT.entity.Report;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddReportRequest {

    private String reportType;

    private Integer reportFromUserNo = null;

    private Integer reportToUserNo = null;

    private String reportContent;

    public Report toEntity() {
        return Report.builder()
                .reportType(reportType)
                .reportContent(reportContent)
                .reportFromUserNo(reportFromUserNo)
                .reportToUserNo(reportToUserNo)
                .build();
    }

    public void setReportFromUserNo(int reportFromUserNo) {
        this.reportFromUserNo = reportFromUserNo;
    }

    public void setReportToUserNo(int reportToUserNo) {
        this.reportToUserNo = reportToUserNo;
    }
}
