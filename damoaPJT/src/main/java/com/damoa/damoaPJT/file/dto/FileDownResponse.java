package com.damoa.damoaPJT.file.dto;

import com.damoa.damoaPJT.entity.Board;
import com.damoa.damoaPJT.entity.File;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FileDownResponse {

    private String originalName;
    private String path;
    private int no;

    public FileDownResponse(String originalName, String path, int no){
        this.originalName = originalName;
        this.path = path;
        this.no = no;
    }

    @Builder
    public FileDownResponse(File file) {
        this.originalName = file.getOriginalName();
        this.path = file.getPath();
        this.no = file.getFileNo();
    }

}
