package com.damoa.damoaPJT.comment;

import com.damoa.damoaPJT.entity.Comment;
import com.damoa.damoaPJT.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    @Query("SELECT c FROM Comment c " +
            "LEFT JOIN FETCH c.user u " +
            "WHERE c.review.reviewNo = :reviewNo")
    List<Comment> findByReviewNoWithUser(@Param("reviewNo") int reviewNo);

    Optional<Comment> findByCommentNo(int commentNo);


}