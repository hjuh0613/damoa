package com.damoa.damoaPJT.chat;

import com.damoa.damoaPJT.chat.dto.ChatRoomResponse;
import com.damoa.damoaPJT.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRepository extends JpaRepository<ChatRoom, Integer> {

    @Query("SELECT c FROM ChatRoom c WHERE c.chatBoard.boardNo = :boardNo AND c.categoryNo = :categoryNo AND (c.chatBuyerUser.userNo = :buyerNo OR c.chatSellerUser.userNo = :sellerNo)")
    Optional<ChatRoom> findChatRoomByCriteria(@Param("boardNo") int boardNo,
                                              @Param("categoryNo") int categoryNo,
                                              @Param("buyerNo") int buyerNo,
                                              @Param("sellerNo") int sellerNo);;

    @Query("SELECT c FROM ChatRoom c WHERE c.chatBuyerUser.userNo = :buyerNo OR c.chatSellerUser.userNo = :sellerNo")
    List<ChatRoom> findChatRoomsByBuyerAndSeller(@Param("buyerNo") int buyerNo,
                                                 @Param("sellerNo") int sellerNo);

}
