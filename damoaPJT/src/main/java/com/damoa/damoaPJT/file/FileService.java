package com.damoa.damoaPJT.file;

import com.damoa.damoaPJT.file.dto.FileListByBoardNoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FileService {

    private final FileRepository fileRepository;

    public List<FileListByBoardNoResponse> getFileListByBoardNoAndBoardType(int boardNo, int boardType){

        return fileRepository.findByBoardNoAndBoardType(boardNo, boardType).stream()
                .map(FileListByBoardNoResponse::new)
                .toList();

    }

}