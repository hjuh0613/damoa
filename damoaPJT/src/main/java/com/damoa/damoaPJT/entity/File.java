package com.damoa.damoaPJT.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name="file")
public class File {

    @Id
    @Column(name = "file_no" )
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int fileNo;

    @Column(name = "original_name")
    private String originalName;

    @Column(name = "path")
    private String path;

    @Column(name = "board_no")
    private int boardNo;

    @Column(name = "board_type")
    private int boardType;

    @Column(name = "size")
    private long size;

    @Builder
    public File(int fileNo, String originalName, String path, int boardNo, int boardType, long size){
        this.fileNo = fileNo;
        this.originalName = originalName;
        this.path = path;
        this.boardNo = boardNo;
        this.boardType = boardType;
        this.size = size;
    }

}