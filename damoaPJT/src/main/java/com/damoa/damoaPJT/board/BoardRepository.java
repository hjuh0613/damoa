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

    // '후기게시판'을 제외한 가장 많은 하트를 받은 게시글을 조회하는 쿼리
    @Query("SELECT b FROM Board b " +
            "JOIN Heart h ON b.boardNo = h.no " +
            "WHERE h.type <> '후기게시판' " +
            "GROUP BY b " +
            "ORDER BY COUNT(h.no) DESC")
    List<Board> findBoardsWithMostHeartsExcludingReview();

    // '후기게시판'을 제외한 내가 찜한 게시글을 조회하는 쿼리
    @Query(value = "SELECT b.* FROM board b JOIN heart h ON b.board_no = h.no " +
            "WHERE h.type != '후기게시판' AND h.user_no = :userNo",
            nativeQuery = true)
    Page<Board> findBoardsByUserNo(Pageable pageable, @Param("userNo") int userNo);

    // 채팅에서 구매확정을 할 때 boardNo와 categoryNo로 게시글을 조회하는 쿼리
    @Query("SELECT b FROM Board b WHERE b.boardNo = :boardNo AND b.category.categoryNo = :categoryNo")
    Optional<Board> findByBoardNoAndCategoryNo(@Param("boardNo") int boardNo, @Param("categoryNo") int categoryNo);

    // 내가 판매한 내역을 조회하는 쿼리
    @Query("SELECT b FROM Board b WHERE b.user.userNo = :userNo AND b.boardIsPurchase = :boardIsPurchase")
    Page<Board> findByUserNoAndBoardIsPurchase(
            Pageable pageable,
            @Param("userNo") int userNo,
            @Param("boardIsPurchase") int boardIsPurchase
    );

}
