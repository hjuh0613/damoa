package com.damoa.damoaPJT.heart;

import com.damoa.damoaPJT.user.dto.CustomUserDetails;
import com.damoa.damoaPJT.heart.dto.AddHeartRequest;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class HeartController {

    private final HeartService heartService;

    // 찜하기 버튼 색 구분 위한 비동기 요청
    @GetMapping("/getHeart")
    @ResponseBody
    public boolean getHeart(@AuthenticationPrincipal CustomUserDetails user, @RequestBody AddHeartRequest addHeartRequest){

        addHeartRequest.setUserNo(user.getUserNo());

        return heartService.getHeartByBoardNo(addHeartRequest);
    }

    // 찜하기
    @PostMapping("/addHeart")
    @ResponseBody // 비동기 요청 시 반환 값을 view 경로로 인식하지 않도록 함
    public String addHeart(@AuthenticationPrincipal CustomUserDetails user, @RequestBody AddHeartRequest addHeartRequest) {

        addHeartRequest.setUserNo(user.getUserNo());

        switch (addHeartRequest.getType()) {
            case "1" -> addHeartRequest.setType("컴퓨터/노트북");
            case "2" -> addHeartRequest.setType("스마트폰/태블릿");
            case "3" -> addHeartRequest.setType("워치");
            case "4" -> addHeartRequest.setType("음향기기");
            case "5" -> addHeartRequest.setType("생활가전");
            case "6" -> addHeartRequest.setType("기타");
            case "7" -> addHeartRequest.setType("후기게시판");
        }

        return heartService.addOrDeleteHeart(addHeartRequest);
    }
}
