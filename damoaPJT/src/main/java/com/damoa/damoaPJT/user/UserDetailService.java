package com.damoa.damoaPJT.user;

import com.damoa.damoaPJT.entity.User;
import com.damoa.damoaPJT.user.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) {
        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("id를 찾지 못함 id ; " + userId));

        return CustomUserDetails.builder()
                .userId(user.getUserId())
                .userPw(user.getUserPw())
                .userNames(user.getUserNames())
                .userPhone(user.getUserPhone())
                .userEmail(user.getUserEmail())
                .userNickname(user.getUserNickname())
                .userAddress(user.getUserAddress())
                .userYn(user.getUserYn())
                .userNo(user.getUserNo())
                .userRole(user.getUserRole())
                .build();
    }
}
