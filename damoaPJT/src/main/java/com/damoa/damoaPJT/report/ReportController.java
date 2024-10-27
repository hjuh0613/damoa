package com.damoa.damoaPJT.report;

import com.damoa.damoaPJT.report.dto.AddReportRequest;
import com.damoa.damoaPJT.report.dto.DeleteUserRequest;
import com.damoa.damoaPJT.user.UserService;
import com.damoa.damoaPJT.user.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RequiredArgsConstructor
@Controller
public class ReportController {

    private final ReportService reportService;

    private final UserService userService;

    @GetMapping("/reportList")
    public String getReportList(Model model) {
        model.addAttribute("reportList", reportService.findAllReport());

        return "/report/reportList";
    }


	// 판매게시판 신고
    @PostMapping("/addReport")
    @ResponseBody
    public int addReport(@RequestPart(value = "sendData") AddReportRequest addReportRequest,
                         @RequestPart(value = "file", required = false) List<MultipartFile> file,
                         @AuthenticationPrincipal CustomUserDetails user) throws Exception {

        addReportRequest.setReportFromUserNo(user.getUserNo());

        return reportService.addReport(addReportRequest, file);
    }

    // 유저 탈퇴 처리
    @PostMapping("/deleteUser")
    @ResponseBody
    public void deleteUser(@RequestBody DeleteUserRequest deleteUserRequest) {

        userService.deleteUser(deleteUserRequest);
    }

}
