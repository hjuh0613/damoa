package com.damoa.damoaPJT.file;

import com.damoa.damoaPJT.file.dto.FileDownResponse;
import com.damoa.damoaPJT.file.dto.FileListByBoardNoResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class FileController {

    private final FileUtil fileUtil;
    private final FileService fileService;


    @GetMapping("/board/downloadBoardFile.do")
    public void downFile(HttpServletResponse response, @RequestParam("fileNo") int fileNo) throws Exception{

        // fileNo 기반 파일 정보 탐색
        FileDownResponse fileDownResponse = fileService.getFileByFileNo(fileNo);

        fileUtil.downFile(response, fileDownResponse);

    }

    // 파일 정보 획득
    @GetMapping("/findFile")
    @ResponseBody
    public List<FileListByBoardNoResponse> getFileDetail(@RequestParam(value = "reportNo") int reportNo){

        return fileService.getFileListByBoardNoAndBoardType(reportNo, 8);
    }

}
