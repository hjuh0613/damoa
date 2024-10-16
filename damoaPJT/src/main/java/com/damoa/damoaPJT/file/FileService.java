package com.damoa.damoaPJT.file;

import com.damoa.damoaPJT.file.dto.FileDownResponse;
import com.damoa.damoaPJT.file.dto.FileListByBoardNoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;

    // fileNo 기반 파일 정보 탐색
    public FileDownResponse getFileByFileNo(int fileNo){
        return fileRepository.findByFileNo(fileNo)
                .map(FileDownResponse::new)
                .orElseThrow(() -> new RuntimeException("File not found"));
    }

    // boardNo, BoardType 기반 파일 정보 탐색
    public List<FileListByBoardNoResponse> getFileListByBoardNoAndBoardType(int boardNo, int boardType){

        return fileRepository.findByBoardNoAndBoardType(boardNo, boardType).stream()
                .map(FileListByBoardNoResponse::new)
                .toList();

    }

}