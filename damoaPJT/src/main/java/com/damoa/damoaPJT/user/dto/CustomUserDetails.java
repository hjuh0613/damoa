package com.damoa.damoaPJT.user.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
public class CustomUserDetails implements UserDetails {

    private String userId;

    private String userPw;

    private String userNames;

    private String userPhone;

    private String userEmail;

    private String userNickname;

    private String userAddress;

    private int userYn;

    private int userNo;

    @Builder
    public CustomUserDetails(String userId
            , String userPw
            , String userNames
            , String userPhone
            , String userEmail
            , String userNickname
            , String userAddress
            , int userYn
            , int userNo) {
        this.userId = userId;
        this.userPw = userPw;
        this.userNames = userNames;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.userNickname = userNickname;
        this.userAddress = userAddress;
        this.userYn = userYn;
        this.userNo = userNo;
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
}
