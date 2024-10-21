package com.damoa.damoaPJT.heart.dto;

import com.damoa.damoaPJT.entity.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MyHeartBoardResponse {

    private int boardNo;

    private String boardTitle;

    private int boardPrice;

    private LocalDateTime boardDate;

    private int categoryNo;

    @Builder
    public MyHeartBoardResponse(Board board) {
        this.boardNo = board.getBoardNo();
        this.boardTitle = board.getBoardTitle();
        this.boardPrice = board.getBoardPrice();
        this.boardDate = board.getBoardDate();
        this.categoryNo = board.getCategory().getCategoryNo();
    }
}
