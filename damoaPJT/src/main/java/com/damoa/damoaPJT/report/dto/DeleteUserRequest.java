package com.damoa.damoaPJT.report.dto;

import com.damoa.damoaPJT.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DeleteUserRequest {

    private String toUserNickname;

    public User toEntity() {
        return User.builder()
                .userNickname(toUserNickname)
                .build();
    }
}
