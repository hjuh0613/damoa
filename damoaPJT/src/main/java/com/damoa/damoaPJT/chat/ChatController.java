package com.damoa.damoaPJT.chat;

import com.damoa.damoaPJT.chat.dto.ChatRoomGoRequest;
import com.damoa.damoaPJT.user.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/chat")
    public String getChat(@AuthenticationPrincipal CustomUserDetails user
            , @RequestParam(value="roomNo", defaultValue="0") int roomNo
            , Model model){

        // 로그인한 유저가 포함된 채팅방 전체 조회
        model.addAttribute("roomList", chatService.findChatRoomsByBuyerAndSeller(user.getUserNo()));

        // 채팅 페이지 직접 접근 시에는 전체 채팅방을 기본으로 보여줌
        model.addAttribute("selectRoomNo", roomNo);

        return "/chat/chat";
    }

    // 판매 게시판에서 '거래' 버튼 클릭시
    @GetMapping("/goRoom")
    public String goRoom(@AuthenticationPrincipal CustomUserDetails user
            , @ModelAttribute ChatRoomGoRequest chatRoomGoRequest
            , Model model){

        chatRoomGoRequest.setChatBuyerUserNo(user.getUserNo());

        // 채팅 페이지에서 활성화 해야할 채팅 room No을 획득
        int roomNo = chatService.goRoom(chatRoomGoRequest);

        return "redirect:/chat?roomNo=" + roomNo;
    }

}
