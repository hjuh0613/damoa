package com.damoa.damoaPJT.report;

import com.damoa.damoaPJT.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Integer> {

}
