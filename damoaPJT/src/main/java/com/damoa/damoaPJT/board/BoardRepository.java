package com.damoa.damoaPJT.board;

import com.damoa.damoaPJT.board.dto.BoardListResponse;
import com.damoa.damoaPJT.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<Board> findByCategory_CategoryNo(int categoryNo);
}
