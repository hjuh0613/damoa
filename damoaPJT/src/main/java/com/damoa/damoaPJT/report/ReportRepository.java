package com.damoa.damoaPJT.report;

import com.damoa.damoaPJT.entity.Report;
import com.damoa.damoaPJT.report.dto.ReportListDetailResponse;
import com.damoa.damoaPJT.report.dto.ReportListResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Integer> {

}
