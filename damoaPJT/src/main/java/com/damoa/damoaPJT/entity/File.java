package com.damoa.damoaPJT.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // 엔티티 등록
@Getter // getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자
@Table(name = "file")
public class File {
        
    // 복합키
    @Id
    @EmbeddedId
    private FilePK filePK;

    @Column(name = "file_name", length = 1000)
    private String fileName;

    @Column(name = "file_path", length = 1000)
    private String filePath;

    @Builder
    public File(FilePK filePK, String fileName, String filePath){
        this.filePK = filePK;
        this.fileName = fileName;
        this.filePath = filePath;
    }
}
