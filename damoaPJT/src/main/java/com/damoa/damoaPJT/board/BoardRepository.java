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
    @Query("SELECT b FROM Board b WHERE b.user.userNo = :userNo AND b.boardIsPurchase = 1")
    Page<Board> findByUserNoAndBoardIsPurchase(
            Pageable pageable,
            @Param("userNo") int userNo
    );


    // 검색어의 그래프 표현 시 x축 값
    @Query(value = "SELECT DATE_FORMAT(board_date, '%Y-%m') AS month " +
            "FROM board " +
            "WHERE board_date >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 4 MONTH), '%Y-%m-01') " +
            "AND board_title LIKE %:keyword% " +
            "GROUP BY month " +
            "ORDER BY month",
            nativeQuery = true)
    List<String> findMonthsByKeyword(@Param("keyword") String keyword);

    // 통계 기능 관련 x축 데이터에 대한 최고가 list
    @Query(value = "SELECT MAX(board_price) AS max_price " +
            "FROM board " +
            "WHERE board_date >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 4 MONTH), '%Y-%m-01') " +
            "AND board_title LIKE %:keyword% " +
            "GROUP BY DATE_FORMAT(board_date, '%Y-%m') " +
            "ORDER BY DATE_FORMAT(board_date, '%Y-%m')",
            nativeQuery = true)
    List<Integer> findMaxPriceByKeyword(@Param("keyword") String keyword);

    // 통계 기능 관련 x축 데이터에 대한 평균가 list
    @Query(value = "SELECT AVG(board_price) AS avg_price " +
            "FROM board " +
            "WHERE board_date >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 4 MONTH), '%Y-%m-01') " +
            "AND board_title LIKE %:keyword% " +
            "GROUP BY DATE_FORMAT(board_date, '%Y-%m') " +
            "ORDER BY DATE_FORMAT(board_date, '%Y-%m') ",
            nativeQuery = true)
    List<Integer> findAveragePriceByKeyword(@Param("keyword") String keyword);


    // 통계 기능 관련 x축 데이터 대한 최저가 list
    @Query(value = "SELECT MIN(board_price) AS min_price " +
            "FROM board " +
            "WHERE board_date >= DATE_FORMAT(DATE_SUB(NOW(), INTERVAL 4 MONTH), '%Y-%m-01') " +
            "AND board_title LIKE %:keyword% " +
            "GROUP BY DATE_FORMAT(board_date, '%Y-%m') " +
            "ORDER BY DATE_FORMAT(board_date, '%Y-%m') ",
            nativeQuery = true)
    List<Integer> findMinPriceByKeyword(@Param("keyword") String keyword);

    // 역대 가격정보
    @Query(value = "SELECT IFNULL(MAX(b.board_price), 0) AS max_price, " +
            "IFNULL(AVG(b.board_price), 0) AS avg_price, " +
            "IFNULL(MIN(b.board_price), 0) AS min_price " +
            "FROM board b " +
            "WHERE b.board_title LIKE %:keyword%",
            nativeQuery = true)
    Object[] findPriceStatisticsByKeyword(@Param("keyword") String keyword);

}
