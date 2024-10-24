package com.damoa.damoaPJT.chat.dto;

import com.damoa.damoaPJT.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class IsPurchaseRequest {

    private int chatRoomNo;

    public ChatRoom toEntity() {
        return ChatRoom.builder()
                .chatRoomNo(chatRoomNo)
                .build();
    }
}
