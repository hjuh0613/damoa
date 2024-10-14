package com.damoa.damoaPJT.report;

import com.damoa.damoaPJT.report.dto.AddReportRequest;
import com.damoa.damoaPJT.user.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Controller
public class ReportController {

    private final ReportService reportService;

    @PostMapping("/addReport")
    @ResponseBody
    public int addReport(@RequestPart(value = "sendData") AddReportRequest addReportRequest,
                         @RequestPart(value = "file", required = false) MultipartFile file,
                         @AuthenticationPrincipal CustomUserDetails user) throws Exception {

        addReportRequest.setReportFromUserNo(user.getUserNo());

        return reportService.addReport(addReportRequest, file);
    }
}
