package com.damoa.damoaPJT.report;

import com.damoa.damoaPJT.entity.Report;
import com.damoa.damoaPJT.report.dto.ReportListDetailResponse;
import com.damoa.damoaPJT.report.dto.ReportListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Integer> {

    @Query("SELECT new com.damoa.damoaPJT.report.dto.ReportListDetailResponse(r.reportNo, r.reportContent, r.reportFromUserNo, u1.userNickname, " +
            "r.reportToUserNo, u2.userNickname, r.reportType) " +
            "FROM Report r " +
            "LEFT JOIN User u1 ON r.reportFromUserNo = u1.userNo " +
            "LEFT JOIN User u2 ON r.reportToUserNo = u2.userNo")
    public List<ReportListDetailResponse> getReportAll();

}
