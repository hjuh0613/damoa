package com.damoa.damoaPJT.user.dto;

import com.damoa.damoaPJT.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDetailUpdateRequest {

    private String userId;

    private String userPw;

    private String userName;

    private String userPhone;

    private String userEmail;

    private String userNickname;

    private String userRegion;

    private String userAddress;

    public User toEntity() {
        return User.builder()
                .userId(userId)
                .userPw(userPw)
                .userName(userName)
                .userPhone(userPhone)
                .userEmail(userEmail)
                .userNickname(userNickname)
                .userRegion(userRegion)
                .userAddress(userAddress)
                .build();
    }
}
