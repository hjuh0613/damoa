package com.damoa.damoaPJT.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // 엔티티 등록
@Getter // getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자
@Table(name = "report")
public class Report {
    
    // 복합키
    @Id
    @EmbeddedId
    private ReportPK reportPK;

    @Column(name = "report_nick", length = 45, nullable = false)
    private String reportNick;

    @Column(name = "report_content", length = 900, nullable = false)
    private String reportContent;

    @Column(name = "report_file", length = 1000, nullable = false)
    private String reportFile;
}
