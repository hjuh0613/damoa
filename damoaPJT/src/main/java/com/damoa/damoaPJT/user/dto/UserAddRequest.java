package com.damoa.damoaPJT.user.dto;

import com.damoa.damoaPJT.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
public class UserAddRequest {

    private String userId;

    private String userPw;

    private String userEmail;

    private String userNames;

    private String userNickname;

    private String userPhone;

    private String userAddress;

}
