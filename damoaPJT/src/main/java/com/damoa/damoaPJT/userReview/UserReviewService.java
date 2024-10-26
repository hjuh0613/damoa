package com.damoa.damoaPJT.userReview;

import com.damoa.damoaPJT.entity.File;
import com.damoa.damoaPJT.entity.Review;
import com.damoa.damoaPJT.file.FileRepository;
import com.damoa.damoaPJT.file.FileUtil;
import com.damoa.damoaPJT.file.dto.FileAddRequest;
import com.damoa.damoaPJT.heart.dto.MyHeartBoardResponse;
import com.damoa.damoaPJT.heart.dto.MyHeartReviewResponse;
import com.damoa.damoaPJT.user.dto.MyReviewResponse;
import com.damoa.damoaPJT.userReview.dto.ReviewAddRequest;
import com.damoa.damoaPJT.userReview.dto.UserReviewListResponse;
import com.damoa.damoaPJT.userReview.dto.UserReviewUpdateRequest;
import com.damoa.damoaPJT.userReview.dto.UserReviewUpdateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserReviewService {

    private final UserReviewRepository userReviewRepository;
    private final FileRepository fileRepository;
    private final FileUtil fileUtil;

    @Transactional
    public int addReview(ReviewAddRequest reviewAddRequest, List<MultipartFile> files) throws Exception{

        // 게시글 저장
        int addReviewNo = userReviewRepository.save(reviewAddRequest.toEntity()).getReviewNo();

        // 파일 저장 로직
        // board_type 7 = 후기 게시판
        List<FileAddRequest> fileAddRequestList = fileUtil.storeFile(files, addReviewNo, 7);

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

        return addReviewNo;
    }

    public Page<UserReviewListResponse> findAllUserReview(int page) {

        Pageable pageable = PageRequest.of(page, 10);

        return userReviewRepository.findAllByOrderByReviewNoDesc(pageable)
                .map(UserReviewListResponse::new);
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
    public Page<MyReviewResponse> findByLogInUserId(int myReviewPage, int userNo) {

        Pageable pageable = PageRequest.of(myReviewPage, 10);

        return userReviewRepository.findByUserUserNo(pageable, userNo)
                .map(MyReviewResponse::new);
    }

    // 제목에서 특정 검색어가 포함된 list get
    public Page<UserReviewListResponse> getUserReviewListBySearch(int page, String reviewSearch) {

        Pageable pageable = PageRequest.of(page, 5);

        return userReviewRepository.findByTitleContaining(pageable, reviewSearch)
                .map(UserReviewListResponse::new);
    }

    // 내가 찜한 판매게시글 불러오기
    public Page<MyHeartReviewResponse> findReviewByHeartTypeAndUser(int page, int userNo) {

        Pageable pageable = PageRequest.of(page, 10);

        return userReviewRepository.findReviewsByUserNo(pageable, userNo)
                .map(MyHeartReviewResponse::new);
    }

}
