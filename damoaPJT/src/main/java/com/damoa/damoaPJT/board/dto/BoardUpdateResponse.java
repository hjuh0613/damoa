package com.damoa.damoaPJT.board.dto;

import com.damoa.damoaPJT.entity.Board;
import com.damoa.damoaPJT.entity.Category;
import lombok.Getter;

@Getter
public class BoardUpdateResponse {

    private int boardNo;

    private String boardTitle;

    private String boardContent;

    private String boardLocation;

    private int boardPrice;

    private int categoryNo;

    public BoardUpdateResponse(Board entity) {
        this.boardNo = entity.getBoardNo();
        this.boardTitle = entity.getBoardTitle();
        this.boardContent = entity.getBoardContent();
        this.boardLocation = entity.getBoardLocation();
        this.boardPrice = entity.getBoardPrice();
        this.categoryNo = entity.getCategory().getCategoryNo();
    }
}
