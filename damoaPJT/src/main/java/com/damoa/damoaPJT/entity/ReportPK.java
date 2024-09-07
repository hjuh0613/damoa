package com.damoa.damoaPJT.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Embeddable
@NoArgsConstructor
public class ReportPK implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_no", nullable = false)
    private int reportNo;

    @Column(name = "report_type", length = 45, nullable = false)
    private String reportType;

    public ReportPK(int reportNo, String reportType) {
        this.reportNo = reportNo;
        this.reportType = reportType;
    }
}
