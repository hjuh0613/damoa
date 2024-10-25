package com.damoa.damoaPJT.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity // 엔티티 등록
@Getter // getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자
@Table(name = "user_datail")
public class UserDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1씩 자동 증가
    @Column(name = "ud_no", nullable = false)
    private int userDetailNo;

    @OneToOne
    @JoinColumn(name = "user_no")
    private User user;

    @CreatedDate
    @Column(name = "ud_joindate", nullable = false)
    private LocalDateTime udJoinDate;

    @Column(name = "ud_penaltyday", nullable = true)
    private int udPenaltyDay;

    @Column(name = "ud_penaltycount", nullable = true)
    private int udPenaltyCount;

    @Builder
    public UserDetail(int userDetailNo,User user, int udPenaltyDay, int udPenaltyCount, LocalDateTime udJoinDate){
        this.userDetailNo = userDetailNo;
        this.user = user;
        this.udPenaltyDay = udPenaltyDay;
        this.udPenaltyCount = udPenaltyCount;
        this.udJoinDate = udJoinDate;
    }
}
