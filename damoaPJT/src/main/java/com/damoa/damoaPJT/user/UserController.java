package com.damoa.damoaPJT.user;

import com.damoa.damoaPJT.entity.UserDetail;
import com.damoa.damoaPJT.user.dto.UserAddRequest;
import com.damoa.damoaPJT.user.dto.UserDetailUpdateRequest;
import com.damoa.damoaPJT.user.dto.UserPwUpdateRequest;
import com.damoa.damoaPJT.user.dto.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    // 로그인
    @GetMapping("/login")
    public String login(Model model) {

        return "/login/pages-login";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return  "redirect:/login";
    }


    // 회원가입
    @GetMapping("/register")
    public String register(Model model) {

        return "/login/pages-register";
    }

    // 회원가입 완료 후 로그인 화면으로 이동
    @PostMapping("/register")
    public String register(UserAddRequest request) {
        userService.save(request);
        return "redirect:/login";
    }

    // 로그인 기능 구현 전 임시로 default 값 admin 넣음
    @GetMapping("/userDetail")
    public String getUserDetail(@AuthenticationPrincipal UserDetails user, Model model) {

        model.addAttribute("UserDetail", userService.getUserDetail(user.getUsername()));

        return "/user/userDetail";
    }

    // 사용자 개인정보 수정
    @PostMapping("/userDetailUpdate")
    public String putUserDetailUpdate(@ModelAttribute UserDetailUpdateRequest userDetailUpdateRequest, Model model) {

        userService.userDetailUpdate(userDetailUpdateRequest);

        return "redirect:/userDetail";
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
