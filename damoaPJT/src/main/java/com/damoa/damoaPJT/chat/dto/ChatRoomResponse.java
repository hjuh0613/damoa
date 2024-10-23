package com.damoa.damoaPJT.chat.dto;

import com.damoa.damoaPJT.entity.Board;
import com.damoa.damoaPJT.entity.ChatRoom;
import com.damoa.damoaPJT.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ChatRoomResponse {

    private int chatRoomNo;
    private Board chatBoard;
    private int categoryNo;
    private User chatSellerUser;
    private User chatBuyerUser;


    @Builder
    public ChatRoomResponse(ChatRoom chatRoom) {
        this.chatRoomNo = chatRoom.getChatRoomNo();
        this.chatBoard = chatRoom.getChatBoard();
        this.categoryNo = chatRoom.getCategoryNo();
        this.chatSellerUser = chatRoom.getChatSellerUser();
        this.chatBuyerUser = chatRoom.getChatBuyerUser();
    }

}
