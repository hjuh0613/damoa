package com.damoa.damoaPJT.report;

import com.damoa.damoaPJT.entity.File;
import com.damoa.damoaPJT.file.FileRepository;
import com.damoa.damoaPJT.file.FileUtil;
import com.damoa.damoaPJT.file.dto.FileAddRequest;
import com.damoa.damoaPJT.report.dto.AddReportRequest;
import com.damoa.damoaPJT.report.dto.ReportListDetailResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReportService {

    private final ReportRepository reportRepository;
    private final FileUtil fileUtil;
    private final FileRepository fileRepository;

    public List<ReportListDetailResponse> findAllReport() {
        return reportRepository.findAll().stream()
                .map(ReportListDetailResponse::new)
                .toList();
    }

    // 판매게시판 신고
    @Transactional
    public int addReport(AddReportRequest addReportRequest, List<MultipartFile> file) throws Exception {

        // 신고 내용 DB 저장
        int addReportNo = reportRepository.save(addReportRequest.toEntity()).getReportNo();

        // 파일 저장
        List<FileAddRequest> fileAddRequestList = fileUtil.storeFile(file, addReportNo, 8);

        // 파일 데이터 DB 저장
        fileRepository.saveAll(fileAddRequestList.stream()
                .map(fileAddRequest -> File.builder()
                        .boardType(fileAddRequest.getBoard_type())
                        .path(fileAddRequest.getPath())
                        .originalName(fileAddRequest.getOriginalName())
                        .boardNo(fileAddRequest.getNo())
                        .size(fileAddRequest.getSize())
                        .build())
                .collect(Collectors.toList()));

        return addReportNo;

    }
}
