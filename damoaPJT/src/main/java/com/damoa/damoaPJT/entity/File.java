package com.damoa.damoaPJT.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
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
    private int no;

    @Column(name = "board_type")
    private int board_type;

    @Column(name = "size")
    private long size;

    @Builder
    public File(int fileNo, String originalName, String path, int no, int board_type, long size){
        this.fileNo = fileNo;
        this.originalName = originalName;
        this.path = path;
        this.no = no;
        this.board_type = board_type;
        this.size = size;
    }

}