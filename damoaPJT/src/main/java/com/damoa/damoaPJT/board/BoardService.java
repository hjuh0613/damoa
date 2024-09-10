package com.damoa.damoaPJT.board;

import com.damoa.damoaPJT.board.dto.BoardListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    public List<BoardListResponse> findByIdBoard(int categoryNo) {
        return boardRepository.findByCategory_CategoryNo(categoryNo).stream()
                .map(BoardListResponse::new)
                .toList();
    }

    public BoardListResponse getProduct(int boardNo) {
        return boardRepository.findByBoardNo(boardNo)
                .map(BoardListResponse::new)
                .orElseThrow(() -> new RuntimeException("Board not found"));
    }
}
