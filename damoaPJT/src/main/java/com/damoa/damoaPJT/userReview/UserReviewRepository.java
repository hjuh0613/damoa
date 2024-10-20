package com.damoa.damoaPJT.userReview;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.damoa.damoaPJT.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserReviewRepository extends JpaRepository<Review, Integer> {

    Page<Review> findAllByOrderByReviewNoDesc(Pageable pageable);

    Optional<Review> findByReviewNo(int reviewNo);

    List<Review> findByUserUserNo(int userNo);


}
