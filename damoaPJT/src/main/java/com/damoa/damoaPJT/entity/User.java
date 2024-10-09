package com.damoa.damoaPJT.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity // 엔티티 등록
@Getter // getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no", nullable = false)
    private int userNo;

    @Column(name = "user_id", length = 45, nullable = false)
    private String userId;

    @Column(name = "user_pw", length = 45, nullable = false)
    private String userPw;

    @Column(name = "user_name", length = 45, nullable = false)
    private String userName;

    @Column(name = "user_phone", length = 45, nullable = false)
    private String userPhone;

    @Column(name = "user_email", length = 45, nullable = false)
    private String userEmail;

    @Column(name = "user_nickname", length = 45, nullable = false)
    private String userNickname;

    @Column(name = "user_region", length = 45, nullable = false)
    private String userRegion;

    @Column(name = "user_address", length = 45, nullable = false)
    private String userAddress;

    @Column(name = "user_role", nullable = false)
    private int userRole;

    @Column(name = "user_yn", nullable = false)
    private int userYn;

    @OneToMany(mappedBy = "user")
    private List<Board> boards = new ArrayList<Board>();

    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<Comment>();

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<Review>();

    @OneToMany(mappedBy = "user")
    private List<Heart> hearts = new ArrayList<Heart>();

    @OneToMany(mappedBy = "chatFromUser")
    private List<Chat> fromChats = new ArrayList<Chat>();

    @OneToMany(mappedBy = "chatToUser")
    private List<Chat> toChats = new ArrayList<Chat>();

    @OneToOne(mappedBy = "user")
    private UserDetail userDetail;

    @Builder
    public User(int userNo,
                String userId,
                String userPw,
                String userName,
                String userPhone,
                String userEmail,
                String userNickname,
                String userRegion,
                String userAddress){
        this.userNo = userNo;
        this.userId = userId;
        this.userPw = userPw;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userNickname = userNickname;
        this.userRegion = userRegion;
        this.userAddress = userAddress;
    }

    public void update(String userId,
                       String userName,
                       String userPhone,
                       String userEmail,
                       String userNickname,
                       String userRegion,
                       String userAddress){
        this.userId = userId;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userNickname = userNickname;
        this.userRegion = userRegion;
        this.userAddress = userAddress;
    }

    public void updatePw(String userPw){
        this.userPw = userPw;
    }
}
