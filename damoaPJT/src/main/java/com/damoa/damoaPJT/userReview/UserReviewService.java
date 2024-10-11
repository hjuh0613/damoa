package com.damoa.damoaPJT.userReview;

import com.damoa.damoaPJT.entity.Review;
import com.damoa.damoaPJT.user.dto.MyBoardResponse;
import com.damoa.damoaPJT.user.dto.MyReviewResponse;
import com.damoa.damoaPJT.userReview.dto.ReviewAddRequest;
import com.damoa.damoaPJT.userReview.dto.UserReviewListResponse;
import com.damoa.damoaPJT.userReview.dto.UserReviewUpdateRequest;
import com.damoa.damoaPJT.userReview.dto.UserReviewUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserReviewService {

    private final UserReviewRepository userReviewRepository;

    @Transactional
    public void addReview(ReviewAddRequest reviewAddRequest){

        userReviewRepository.save(reviewAddRequest.toEntity());
    }

    public List<UserReviewListResponse> findAllUserReview() {
        return userReviewRepository.findAll().stream()
                .map(UserReviewListResponse::new)
                .toList();
    }

    public UserReviewListResponse getReview(int reviewNo) {
        return userReviewRepository.findByReviewNo(reviewNo)
                .map(UserReviewListResponse::new)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    public UserReviewUpdateResponse findByReviewNo(int reviewNo) {
        return userReviewRepository.findByReviewNo(reviewNo)
                .map(UserReviewUpdateResponse::new)
                .orElseThrow(() -> new RuntimeException("Review update failed"));
    }

    @Transactional
    public UserReviewListResponse updateReview(UserReviewUpdateRequest userReviewUpdateRequest) {

        // 사용자에게 전달받은 reviewNo로 repository에서 조회해서 Entity 클래스에 담기
        Review entity = userReviewRepository.findByReviewNo(userReviewUpdateRequest.getReviewNo())
                .orElseThrow(() -> new RuntimeException("Review update failed"));

        // 사용자에게 전달받은 값(form)로 Entity 클래스 객체 setting
        entity.update(userReviewUpdateRequest.getReviewTitle(), userReviewUpdateRequest.getReviewContent());

        return userReviewRepository.findByReviewNo(entity.getReviewNo())
                .map(UserReviewListResponse::new)
                .orElseThrow(() -> new RuntimeException("Review not found"));
    }

    public void deleteReview(int reviewNo){
        userReviewRepository.deleteById(reviewNo);
    }

    // 특정 사용자가 작성한 모든 후기글 불러오기
    public List<MyReviewResponse> findByLogInUserId(int userNo) {
        return userReviewRepository.findByUserUserNo(userNo).stream()
                .map(MyReviewResponse::new)
                .toList();
    }

}
