package com.damoa.damoaPJT.chat;

import com.damoa.damoaPJT.board.BoardRepository;
import com.damoa.damoaPJT.chat.dto.ChatRoomGoRequest;
import com.damoa.damoaPJT.chat.dto.ChatRoomResponse;
import com.damoa.damoaPJT.chat.dto.IsPurchaseRequest;
import com.damoa.damoaPJT.entity.Board;
import com.damoa.damoaPJT.entity.ChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRepository chatRepository;

    private final BoardRepository boardRepository;
    
    @Transactional
    public int goRoom(ChatRoomGoRequest chatRoomGoRequest){
        
        // 특정 판매글에 대한 채팅방이 이미 있는지 확인
        Optional<ChatRoom> chatRoomOptional = chatRepository.findChatRoomByCriteria(chatRoomGoRequest.getChatBoardNo()
        , chatRoomGoRequest.getCategoryNo()
        , chatRoomGoRequest.getChatBuyerUserNo()
        , chatRoomGoRequest.getChatSellerUserNo());

        int roomNo = 0;

        if(!chatRoomOptional.isPresent()){
            // 특정 판매글에 대한 특정 유저의 채팅방 생성
            roomNo = chatRepository.save(chatRoomGoRequest.toEntity()).getChatRoomNo();
        }else{
            roomNo = chatRoomOptional.get().getChatRoomNo();
        }

        return roomNo;
    }

    // 특정유저가 포함된 채팅방 획득
    @Transactional
    public List<ChatRoomResponse> findChatRoomsByBuyerAndSeller(int userNo){

        return chatRepository.findChatRoomsByBuyerAndSeller(userNo, userNo)
                .stream()
                .map(ChatRoomResponse::new)
                .toList();
    }

    @Transactional
    public void isPurchase(IsPurchaseRequest isPurchaseRequest) {

        // 채팅방 번호로 chatRoom 엔티티
        ChatRoom chatRoom = chatRepository.findByChatRoomNo(isPurchaseRequest.getChatRoomNo())
                .orElseThrow(() -> new RuntimeException("Cannot found chatRoomNo"));

        // 게시글 번호와 카테고리 번호로 board 엔티티
        Board board = boardRepository.findByBoardNoAndCategoryNo(chatRoom.getChatBoard().getBoardNo(), chatRoom.getCategoryNo())
                .orElseThrow(() -> new RuntimeException("Cannot found BoardNo and CategoryNo"));

        board.update(1);


    }

}
