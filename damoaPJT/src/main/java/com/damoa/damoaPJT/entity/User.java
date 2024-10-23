package com.damoa.damoaPJT.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity // 엔티티 등록
@Getter // getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자
@Table(name = "users")
public class User implements UserDetails { // User 를 상속받아 인증 객체로 사용


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_no", nullable = false)
    private int userNo;

    @Column(name = "user_id", length = 45, nullable = false)
    private String userId;

    @Column(name = "user_pw", nullable = false)
    private String userPw;

    @Column(name = "user_name", length = 45, nullable = false)
    private String userNames;

    @Column(name = "user_phone", length = 45, nullable = false)
    private String userPhone;

    @Column(name = "user_email", length = 45, nullable = false)
    private String userEmail;

    @Column(name = "user_nickname", length = 45, nullable = false)
    private String userNickname;

    @Column(name = "user_address", length = 45, nullable = false)
    private String userAddress;

    @Column(name = "user_role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role userRole;

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

    @OneToOne(mappedBy = "user")
    private UserDetail userDetail;

    @Builder
    public User(int userNo,
                String userId,
                String userPw,
                String userNames,
                String userPhone,
                String userEmail,
                String userNickname,
                String userAddress,
                Role userRole,
                int userYn){
        this.userNo = userNo;
        this.userId = userId;
        this.userPw = userPw;
        this.userNames = userNames;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userNickname = userNickname;
        this.userAddress = userAddress;
        this.userRole = userRole;
        this.userYn = userYn;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    // 사용자 ID 반환
    @Override
    public String getUsername() {
        return userId;
    }

    // 사용자 PW 반환
    @Override
    public String getPassword() {
        return userPw;
    }

    // 사용자 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 사용자 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        return true;
    }

    public void update(String userId,
                       String userName,
                       String userPhone,
                       String userEmail,
                       String userNickname,
                       String userAddress){
        this.userId = userId;
        this.userNames = userName;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userNickname = userNickname;
        this.userAddress = userAddress;
    }

    public void delete(int userYn) {
        this.userYn = userYn;
    }

    public void updatePw(String userPw){
        this.userPw = userPw;
    }
}
