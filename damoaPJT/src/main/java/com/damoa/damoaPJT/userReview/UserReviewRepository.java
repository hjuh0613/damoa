package com.damoa.damoaPJT.userReview;

import com.damoa.damoaPJT.entity.Comment;
import com.damoa.damoaPJT.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserReviewRepository extends JpaRepository<Review, Integer> {

    Optional<Review> findByReviewNo(int reviewNo);

    List<Review> findByUserUserNo(int userNo);

    List<Review> findAllByOrderByReviewNoDesc();

}
