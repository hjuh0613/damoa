package com.damoa.damoaPJT.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserPwUpdateRequest {

    private String userId;

    private String userPw;

    private String userNewPw;

    private String userReNewPw;

    @Builder
    public UserPwUpdateRequest(String userId, String userPw, String userNewPw, String userReNewPw){
        this.userId = userId;
        this.userPw = userPw;
        this.userNewPw = userNewPw;
        this.userReNewPw = userReNewPw;
    }

    public UserPwUpdateRequest toEntity(){
        return UserPwUpdateRequest.builder()
                .userId(userId)
                .userPw(userPw)
                .userNewPw(userNewPw)
                .userReNewPw(userReNewPw)
                .build();
    }
}
