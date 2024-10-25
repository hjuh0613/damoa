package com.damoa.damoaPJT.userReview;

import com.damoa.damoaPJT.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.damoa.damoaPJT.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserReviewRepository extends JpaRepository<Review, Integer> {

    Page<Review> findAllByOrderByReviewNoDesc(Pageable pageable);

    Optional<Review> findByReviewNo(int reviewNo);

    Page<Review> findByUserUserNo(Pageable pageable, int userNo);

    @Query("SELECT r FROM Review r WHERE r.reviewTitle LIKE CONCAT('%', :title, '%')")
    Page<Review> findByTitleContaining(Pageable pageable, @Param("title") String title);

    // '후기게시판' 타입에 대한 하트 수가 많은 상위 리뷰 조회
    @Query("SELECT r FROM Review r " +
            "JOIN Heart h ON r.reviewNo = h.no " +
            "WHERE h.type = '후기게시판' " +
            "GROUP BY r " +
            "ORDER BY COUNT(h.no) DESC")
    List<Review> findReviewsWithMostHeartsInReviewBoard();

    // '후기게시판' 타입에 대한 내가 찜한 게시글을 조회하는 쿼리
    @Query(value = "SELECT r.* " +
            "FROM review r JOIN heart h ON r.review_no = h.no " +
            "WHERE h.type = '후기게시판' " +
            "AND h.user_no = :userNo",
            nativeQuery = true)
    Page<Review> findReviewsByUserNo(Pageable pageable, @Param("userNo") int userNo);

}
