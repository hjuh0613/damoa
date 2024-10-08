package com.damoa.damoaPJT.user;

import com.damoa.damoaPJT.entity.UserDetail;
import com.damoa.damoaPJT.user.dto.UserDetailUpdateRequest;
import com.damoa.damoaPJT.user.dto.UserPwUpdateRequest;
import com.damoa.damoaPJT.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    // 로그인 기능 구현 전 임시로 default 값 admin 넣음
    @GetMapping("/userDetail")
    public String getUserDetail(@RequestParam(value = "user_id", defaultValue = "admin") String userId, Model model) {

        model.addAttribute("UserDetail", userService.getUserDetail(userId));

        return "/user/userDetail";
    }

    @PostMapping("/userDetailUpdate")
    public String putUserDetailUpdate(@ModelAttribute UserDetailUpdateRequest userDetailUpdateRequest, Model model) {

        model.addAttribute("UserDetail", userService.userDetailUpdate(userDetailUpdateRequest));

        return "/user/userDetail";
    }

    @PostMapping("/userPwUpdate")
    public String putUserPwUpdate(@ModelAttribute UserPwUpdateRequest userPwUpdateRequest, Model model) {


        // renewPw와 newPw가 다르면 사용자 입력 다시 받기 필요
        if(!userPwUpdateRequest.getUserNewPw().equals(userPwUpdateRequest.getUserReNewPw())){
            model.addAttribute("renewFail", "fail");
        }else {
            UserResponse userResponse = userService.userPwUpdate(userPwUpdateRequest);

            // 사용자 id에 맞는 현재 pw가 다름
            if(userResponse == null){
                model.addAttribute("pwFail", "pwFail");
            }
        }

        model.addAttribute("UserDetail", userService.getUserDetail(userPwUpdateRequest.getUserId()));

        return "/user/userDetail";
    }
}
