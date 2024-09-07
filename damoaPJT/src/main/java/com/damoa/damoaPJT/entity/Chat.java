package com.damoa.damoaPJT.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity // 엔티티 등록
@Getter // getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자
@Table(name = "chat")
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1씩 자동 증가
    @Column(name = "chat_no", nullable = false)
    private int chatNo;

    @Column(name = "chat_content", length = 900, nullable = false)
    private String chatContent;

    @CreatedDate
    @Column(name = "chat_date", nullable = false)
    private LocalDateTime chatDate;

    @Column(name = "chat_file", length = 45)
    private String chatFile;

    @Column(name = "chat_deal", nullable = false)
    private int chatDeal;

    @ManyToOne
    @JoinColumn(name = "chat_from_no")
    private User chatFromUser;

    @ManyToOne
    @JoinColumn(name = "chat_to_no")
    private User chatToUser;
}
