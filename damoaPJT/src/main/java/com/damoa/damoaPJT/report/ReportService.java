package com.damoa.damoaPJT.report;

import com.damoa.damoaPJT.report.dto.AddReportRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReportRepository reportRepository;

    @Transactional
    public int addReport(AddReportRequest addReportRequest, MultipartFile file) throws Exception {

        // 신고 내용 DB 저장
        int addReportNo = reportRepository.save(addReportRequest.toEntity()).getReportNo();

        // 파일 저장

        return addReportNo;

    }
}
