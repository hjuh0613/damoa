package com.damoa.damoaPJT.board.dto;

import com.damoa.damoaPJT.entity.Board;
import com.damoa.damoaPJT.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardUpdateRequest {
    private int boardNo;

    private String boardTitle;

    private String boardContent;

    private String boardLocation;

    private int boardPrice;

    private int categoryNo;

    public Board toEntity() {
        return Board.builder()
                .boardNo(boardNo)
                .boardTitle(boardTitle)
                .boardContent(boardContent)
                .boardLocation(boardLocation)
                .boardPrice(boardPrice)
                .category(Category.builder()
                        .categoryNo(categoryNo)
                        .build())
                .build();
    }

}
