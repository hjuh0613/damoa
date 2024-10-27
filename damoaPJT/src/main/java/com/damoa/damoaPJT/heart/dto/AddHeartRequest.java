package com.damoa.damoaPJT.heart.dto;

import com.damoa.damoaPJT.entity.Heart;
import com.damoa.damoaPJT.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddHeartRequest {

    private String no;
    private String type;
    private Integer userNo = null;

    public Heart toEntity() {
        return Heart.builder()
                .no(Integer.parseInt(no))
                .type(type)
                .user(User.builder()
                        .userNo(userNo)
                        .build())
                .build();
    }

    public void setUserNo(int userNo){
        this.userNo = userNo;
    }

    public void setType(String type){
        this.type = type;
    }

}
