package com.damoa.damoaPJT.heart;

import com.damoa.damoaPJT.user.dto.CustomUserDetails;
import com.damoa.damoaPJT.heart.dto.AddHeartRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class HeartController {

    private final HeartService heartService;

    // 찜하기
    @PostMapping("/addHeart")
    @ResponseBody // 비동기 요청 시 반환 값을 view 경로로 인식하지 않도록 함
    public String addHeart(@AuthenticationPrincipal CustomUserDetails user, @RequestBody AddHeartRequest addHeartRequest) {

        addHeartRequest.setUserNo(user.getUserNo());

        return heartService.addOrDeleteHeart(addHeartRequest);
    }
}
