package com.damoa.damoaPJT.user;

import com.damoa.damoaPJT.entity.Role;
import com.damoa.damoaPJT.entity.User;
import com.damoa.damoaPJT.report.dto.DeleteUserRequest;
import com.damoa.damoaPJT.user.dto.UserAddRequest;
import com.damoa.damoaPJT.user.dto.UserDetailUpdateRequest;
import com.damoa.damoaPJT.user.dto.UserPwUpdateRequest;
import com.damoa.damoaPJT.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public String save(UserAddRequest userAddRequest) {
        return userRepository.save(User.builder()
                .userId(userAddRequest.getUserId())
                .userPw(bCryptPasswordEncoder.encode(userAddRequest.getUserPw()))
                .userEmail(userAddRequest.getUserEmail())
                .userNames(userAddRequest.getUserNames())
                .userNickname(userAddRequest.getUserNickname())
                .userPhone(userAddRequest.getUserPhone())
                .userAddress(userAddRequest.getUserAddress())
                .userYn(1)
                .userRole(Role.ROLE_USER)
                .build()).getUserId();
    }

    public UserResponse getUserDetail(String userId) {

        return userRepository.findByUserId(userId)
                .map(UserResponse::new)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public UserResponse userDetailUpdate(UserDetailUpdateRequest userDetailUpdateRequest) {
        User entity = userRepository.findByUserId(userDetailUpdateRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        entity.update(userDetailUpdateRequest.getUserId()
                , userDetailUpdateRequest.getUserName()
                , userDetailUpdateRequest.getUserPhone()
                , userDetailUpdateRequest.getUserEmail()
                , userDetailUpdateRequest.getUserNickname()
                , userDetailUpdateRequest.getUserAddress()
        );

        return userRepository.findByUserId(entity.getUserId())
                .map(UserResponse::new)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    // 비밀번호 변경
    @Transactional
    public UserResponse userPwUpdate(UserPwUpdateRequest userPwUpdateRequest) {

        User user = userRepository.findByUserId(userPwUpdateRequest.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if(bCryptPasswordEncoder.matches(userPwUpdateRequest.getUserPw(), user.getUserPw())) {
            String newPw = bCryptPasswordEncoder.encode(userPwUpdateRequest.getUserNewPw());
            user.updatePw(newPw);
        }else{
            return null;
        }

        return userRepository.findByUserId(user.getUserId())
                .map(UserResponse::new)
                .orElseThrow(() -> new RuntimeException("User ID not found"));
    }

    // 회원 탈퇴
    @Transactional
    public UserResponse deleteUser(DeleteUserRequest deleteUserRequest) {
        User user = userRepository.findByUserNickname(deleteUserRequest.getToUserNickname())
                .orElseThrow(() -> new RuntimeException("User Delete failed"));

        user.delete(0);

        return userRepository.findByUserNickname(user.getUserNickname())
                .map(UserResponse::new)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

}
