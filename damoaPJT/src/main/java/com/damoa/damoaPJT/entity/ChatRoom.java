package com.damoa.damoaPJT.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // 엔티티 등록
@Getter // getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 생성자
@Table(name = "chatroom")
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 1씩 자동 증가
    @Column(name = "chatroom_no", nullable = false)
    private int chatRoomNo;

    // 어떤 판매글에 대한 채팅인지 확인용도
    @ManyToOne
    @JoinColumn(name = "chatroom_board_no")
    private Board chatBoard;

    // 어떤 판매글에 대한 채팅인지 확인용도
    @Column(name = "chatroom_category_no", nullable = false)
    private int categoryNo;

    @ManyToOne
    @JoinColumn(name = "chatroom_seller_no")
    private User chatSellerUser;

    @ManyToOne
    @JoinColumn(name = "chatroom_buyer_no")
    private User chatBuyerUser;

    @Builder
    public ChatRoom(int chatRoomNo, Board chatBoard, int categoryNo, User chatSellerUser, User chatBuyerUser){
        this.chatRoomNo = chatRoomNo;
        this.chatBoard = chatBoard;
        this.categoryNo = categoryNo;
        this.chatSellerUser = chatSellerUser;
        this.chatBuyerUser = chatBuyerUser;
    }
}
