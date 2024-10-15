package com.damoa.damoaPJT.board;

import com.damoa.damoaPJT.board.dto.BoardAddRequest;
import com.damoa.damoaPJT.board.dto.BoardListResponse;
import com.damoa.damoaPJT.board.dto.BoardUpdateRequest;
import com.damoa.damoaPJT.board.dto.BoardUpdateResponse;
import com.damoa.damoaPJT.entity.File;
import com.damoa.damoaPJT.file.FileRepository;
import com.damoa.damoaPJT.file.FileUtil;
import com.damoa.damoaPJT.file.dto.FileAddRequest;
import com.damoa.damoaPJT.entity.Board;
import com.damoa.damoaPJT.user.dto.MyBoardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final FileRepository fileRepository;
    private final FileUtil fileUtil;

    public List<BoardListResponse> findByIdBoard(int categoryNo) {
        return boardRepository.findByCategory_CategoryNoOrderByBoardNoDesc(categoryNo).stream()
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
                , Integer.parseInt(boardUpdateRequest.getCategoryNo())
        );

        return boardRepository.findByBoardNo(entity.getBoardNo())
                .map(BoardListResponse::new)
                .orElseThrow(() -> new RuntimeException("Board not found"));
    }

    @Transactional
    public int addProduct(BoardAddRequest boardAddRequest, List<MultipartFile> files) throws Exception{

        // 게시글 저장
        int addBoardNo = boardRepository.save(boardAddRequest.toEntity()).getBoardNo();

        // 파일 저장 로직
        List<FileAddRequest> fileAddRequestList = fileUtil.storeFile(files, addBoardNo, boardAddRequest.getCategoryNo());

        // 파일 데이터 DB 저장
        fileRepository.saveAll(fileAddRequestList.stream()
                .map(fileAddRequest -> File.builder()
                        .boardType(fileAddRequest.getBoard_type())
                        .path(fileAddRequest.getPath())
                        .originalName(fileAddRequest.getOriginalName())
                        .boardNo(fileAddRequest.getNo())
                        .size(fileAddRequest.getSize())
                        .build())
                .collect(Collectors.toList()));

        return addBoardNo;
    }

    public void deleteBoard(int boardNo){
        boardRepository.deleteById(boardNo);
    }
    
    // 특정 사용자가 작성한 모든 판매글 불러오기
    public List<MyBoardResponse> findByLogInUserId(int userNo) {
        return boardRepository.findByUserUserNo(userNo).stream()
                .map(MyBoardResponse::new)
                .toList();
    }

}
