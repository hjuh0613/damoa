package com.damoa.damoaPJT.report.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReportListDetailResponse {

    private int reportNo;
    private String reportContent;
    private int reportFromUserNo;
    private String fromUserNickname;
    private int reportToUserNo;
    private String toUserNickname;
    private String reportType;

    public ReportListDetailResponse(int reportNo, String reportContent, int reportFromUserNo, String fromUserNickname,
                                    int reportToUserNo, String toUserNickname, String reportType) {
        this.reportNo = reportNo;
        this.reportContent = reportContent;
        this.reportFromUserNo = reportFromUserNo;
        this.fromUserNickname = fromUserNickname;
        this.reportToUserNo = reportToUserNo;
        this.toUserNickname = toUserNickname;
        this.reportType = reportType;
    }

}