package com.damoa.damoaPJT.file.dto;

import com.damoa.damoaPJT.entity.File;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class FileAddRequest {

    private String originalName;
    private String path;
    private int no;
    private int board_type;
    private long size;

    public FileAddRequest(String originalName, String path, int no, int board_type, long size){
        this.originalName = originalName;
        this.path = path;
        this.no = no;
        this.board_type = board_type;
        this.size = size;
    }

    public File toEntity(){
        return File.builder()
                .originalName(originalName)
                .path(path)
                .boardNo(no)
                .boardType(board_type)
                .size(size)
                .build();
    }

}
