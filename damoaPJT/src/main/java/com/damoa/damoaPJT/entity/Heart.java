package com.damoa.damoaPJT.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // 엔티티 등록
@Getter // getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자
@Table(name = "heart")
public class Heart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1씩 자동 증가
    @Column(name = "heart_no", nullable = false)
    private int heartNo;

    @Column(name = "no", nullable = false)
    private int no;

    @Column(name = "type", nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "user_no")
    private User user;

    @Builder
    public Heart(int heartNo, int no, String type, User user){
        this.heartNo = heartNo;
        this.no = no;
        this.type = type;
        this.user = user;
    }
}
