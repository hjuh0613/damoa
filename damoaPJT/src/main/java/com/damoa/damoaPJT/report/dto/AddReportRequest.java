package com.damoa.damoaPJT.report.dto;

import com.damoa.damoaPJT.entity.Board;
import com.damoa.damoaPJT.entity.Report;
import com.damoa.damoaPJT.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AddReportRequest {

    private Integer reportFromUserNo = null;

    private Integer reportToUserNo = null;

    private String reportContent;

    private int boardNo;

    private int boardTypeNo;

    public Report toEntity() {
        return Report.builder()
                .reportContent(reportContent)
                .reportFromUser(User.builder()
                        .userNo(reportFromUserNo)
                        .build())
                .reportToUser(User.builder()
                        .userNo(reportToUserNo)
                        .build())
                .boardNo(boardNo)
                .boardTypeNo(boardTypeNo)
                .build();
    }

    public void setReportFromUserNo(int reportFromUserNo) {
        this.reportFromUserNo = reportFromUserNo;
    }

    public void setReportToUserNo(int reportToUserNo) {
        this.reportToUserNo = reportToUserNo;
    }
}
