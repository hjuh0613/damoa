package com.damoa.damoaPJT.chat.dto;

import com.damoa.damoaPJT.entity.Board;
import com.damoa.damoaPJT.entity.ChatRoom;
import com.damoa.damoaPJT.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatRoomGoRequest {

    private int chatBoardNo;
    private int categoryNo;
    private int chatSellerUserNo;
    private Integer chatBuyerUserNo = null;

    public ChatRoom toEntity(){
        return ChatRoom.builder()
                .chatBoard(Board.builder()
                        .boardNo(chatBoardNo)
                        .build())
                .categoryNo(categoryNo)
                .chatSellerUser(User.builder()
                        .userNo(chatSellerUserNo)
                        .build())
                .chatBuyerUser(User.builder()
                        .userNo(chatBuyerUserNo)
                        .build())
                .build();
    }

    public void setChatBuyerUserNo(int chatBuyerUserNo){
        this.chatBuyerUserNo = chatBuyerUserNo;
    }
}
