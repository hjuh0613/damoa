package com.damoa.damoaPJT.heart.dto;

import com.damoa.damoaPJT.entity.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyHeartBoardResponse {

    private int boardNo;

    private String boardTitle;

    private String boardContent;

    private LocalDateTime boardDate;

    private String boardLocation;

    private int boardPrice;

    private int categoryNo;

    private int userNo;

    @Builder
    public MyHeartBoardResponse(Board board) {
        this.boardNo = board.getBoardNo();
        this.boardTitle = board.getBoardTitle();
        this.boardContent = board.getBoardContent();
        this.boardDate = board.getBoardDate();
        this.boardLocation = board.getBoardLocation();
        this.boardPrice = board.getBoardPrice();
        this.categoryNo = board.getCategory().getCategoryNo();
        this.userNo = board.getUser().getUserNo();
    }
}
