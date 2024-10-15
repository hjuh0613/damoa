package com.damoa.damoaPJT.file.dto;

import com.damoa.damoaPJT.entity.File;
import lombok.Builder;
import lombok.Getter;

@Getter
public class FileListByBoardNoResponse {

    private int fileNo;
    private String originalName;
    private String path;
    private int boardNo;
    private int boardType;
    private long size;

    @Builder
    public FileListByBoardNoResponse(File file){
        this.boardNo = file.getBoardNo();
        this.boardType = file.getBoardType();
        this.fileNo = file.getFileNo();
        this.path = file.getPath();
        this.originalName = file.getOriginalName();
        this.size = file.getSize();
    }


}
