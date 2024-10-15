package com.damoa.damoaPJT.report;

import com.damoa.damoaPJT.report.dto.AddReportRequest;
import com.damoa.damoaPJT.report.dto.ReportListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReportRepository reportRepository;

    public List<ReportListResponse> findAllReport() {
        return reportRepository.findAll().stream()
                .map(ReportListResponse::new)
                .toList();
    }

    @Transactional
    public int addReport(AddReportRequest addReportRequest, MultipartFile file) throws Exception {

        // 신고 내용 DB 저장
        int addReportNo = reportRepository.save(addReportRequest.toEntity()).getReportNo();

        // 파일 저장


        return addReportNo;

    }
}
