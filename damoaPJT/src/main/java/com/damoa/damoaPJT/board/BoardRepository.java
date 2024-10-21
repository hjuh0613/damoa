package com.damoa.damoaPJT.board;

import com.damoa.damoaPJT.board.dto.BoardListResponse;
import com.damoa.damoaPJT.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Integer> {
    List<Board> findByCategory_CategoryNo(int categoryNo);
    Optional<Board> findByBoardNo(int boardNo);

    Page<Board> findByUserUserNo(Pageable pageable, int userNo);

    Page<Board> findByCategory_CategoryNoOrderByBoardNoDesc(Pageable pageable, int categoryNo);

    // 제목
    @Query("SELECT b FROM Board b WHERE b.boardTitle LIKE CONCAT('%', :title, '%')")
    Page<Board> findByTitleContaining(Pageable pageable, @Param("title") String title);

}
