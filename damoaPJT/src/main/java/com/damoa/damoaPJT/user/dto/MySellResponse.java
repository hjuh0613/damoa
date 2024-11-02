package com.damoa.damoaPJT.user.dto;

import com.damoa.damoaPJT.entity.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class MySellResponse {
    private int boardNo;

    private String boardTitle;

    private int boardPrice;

    private LocalDateTime boardDate;

    private int categoryNo;

    @Builder
    public MySellResponse(Board board) {
        this.boardNo = board.getBoardNo();
        this.boardTitle = board.getBoardTitle();
        this.boardPrice = board.getBoardPrice();
        this.boardDate = board.getBoardDate();
        this.categoryNo = board.getCategory().getCategoryNo();
    }
}
