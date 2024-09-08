package com.damoa.damoaPJT.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model) {

        return "/login/pages-login";
    }

    @GetMapping("/register")
    public String register(Model model) {

        return "/login/pages-register";
    }
}
