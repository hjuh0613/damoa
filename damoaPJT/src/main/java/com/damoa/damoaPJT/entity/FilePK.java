package com.damoa.damoaPJT.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@Embeddable
@NoArgsConstructor
public class FilePK implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_no", nullable = false)
    private int fileNo;

    @Column(name = "file_type", length = 45, nullable = false)
    private String fileType;

    // 복합키
    public FilePK(int fileNo, String fileType) {
        this.fileNo = fileNo;
        this.fileType = fileType;
    }
}
