package com.damoa.damoaPJT.user.dto;

import com.damoa.damoaPJT.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponse {

    private String userId;

    private String userPw;

    private String userName;

    private String userPhone;

    private String userEmail;

    private String userNickname;

    private String userAddress;

    @Builder
    public UserResponse(User user){
        this.userId = user.getUserId();
        this.userPw = user.getUserPw();
        this.userName = user.getUserNames();
        this.userPhone = user.getUserPhone();
        this.userEmail = user.getUserEmail();
        this.userNickname = user.getUserNickname();
        this.userAddress = user.getUserAddress();
    }
}
