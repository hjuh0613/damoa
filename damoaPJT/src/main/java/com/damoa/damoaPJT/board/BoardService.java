package com.damoa.damoaPJT.board;

import com.damoa.damoaPJT.board.dto.BoardAddRequest;
import com.damoa.damoaPJT.board.dto.BoardListResponse;
import com.damoa.damoaPJT.board.dto.BoardUpdateRequest;
import com.damoa.damoaPJT.board.dto.BoardUpdateResponse;
import com.damoa.damoaPJT.entity.Board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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

    public BoardUpdateResponse findByBoardNo(int boardNo) {
        return boardRepository.findByBoardNo(boardNo)
                .map(BoardUpdateResponse::new)
                .orElseThrow(() -> new RuntimeException("Board update failed"));
    }

    @Transactional
    public BoardListResponse updateBoard(BoardUpdateRequest boardUpdateRequest) {
        Board entity = boardRepository.findByBoardNo(boardUpdateRequest.getBoardNo())
                .orElseThrow(() -> new RuntimeException("Board update failed"));

        entity.update(boardUpdateRequest.getBoardTitle()
                , boardUpdateRequest.getBoardContent()
                , boardUpdateRequest.getBoardLocation()
                , boardUpdateRequest.getBoardPrice()
                , boardUpdateRequest.getCategoryNo()
        );

        return boardRepository.findByBoardNo(entity.getBoardNo())
                .map(BoardListResponse::new)
                .orElseThrow(() -> new RuntimeException("Board not found"));
    }

    public int addProduct(BoardAddRequest boardAddRequest, List<MultipartFile> files){

        // 게시글 저장
        int addBoardNo = boardRepository.save(boardAddRequest.toEntity()).getBoardNo();

        return addBoardNo;
    }


}
