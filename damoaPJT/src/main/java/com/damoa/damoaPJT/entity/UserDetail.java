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
@Table(name = "user_datail")
public class UserDetail {

    // 보류
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1씩 자동 증가
    @Column(name = "user_no", nullable = false)
    private int userNo;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_no")
    private User user;

    @CreatedDate
    @Column(name = "ud_joindate", nullable = false)
    private LocalDateTime udJoinDate;

    @Column(name = "ud_penaltyday", nullable = false)
    private int udPenaltyDay;

    @Column(name = "ud_penaltycount", nullable = false)
    private int udPenaltyCount;

    @CreatedDate
    @Column(name = "ud_gpsdate", nullable = false)
    private LocalDateTime udGpsDate;
}
