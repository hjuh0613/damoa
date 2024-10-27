package com.damoa.damoaPJT.heart;

import com.damoa.damoaPJT.board.BoardRepository;
import com.damoa.damoaPJT.entity.Heart;
import com.damoa.damoaPJT.heart.dto.AddHeartRequest;
import com.damoa.damoaPJT.heart.dto.MostHeartBoardResponse;
import com.damoa.damoaPJT.heart.dto.MostHeartReviewResponse;
import com.damoa.damoaPJT.userReview.UserReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class HeartService {

    private final HeartRepository heartRepository;
    private final BoardRepository boardRepository;
    private final UserReviewRepository userReviewRepository;

    @Transactional
    public String addOrDeleteHeart(AddHeartRequest addHeartRequest){

        Optional<Heart> optionalHeart = heartRepository.findByUserUserNoAndNoAndType(
                addHeartRequest.getUserNo()
                , Integer.parseInt(addHeartRequest.getNo())
                , addHeartRequest.getType()
        );

        String returnStr = "";

        if(optionalHeart.isEmpty()) {
            heartRepository.save(addHeartRequest.toEntity());

            returnStr = "add";
        } else {
            heartRepository.deleteById(optionalHeart.get().getHeartNo());

            returnStr = "delete";
        }

        return returnStr;
    }


    // 판매글 찜 수 상위 5개 get
    public List<MostHeartBoardResponse> getBoardTop5() {
        return boardRepository.findBoardsWithMostHeartsExcludingReview()
                .stream()
                .map(MostHeartBoardResponse::new)
                .toList();
    }


    // 후기글 찜 수 상위 5개 get
    public List<MostHeartReviewResponse> getReviewTop5() {
        return userReviewRepository.findReviewsWithMostHeartsInReviewBoard()
                .stream()
                .map(MostHeartReviewResponse::new)
                .toList();
    }

    // 이 판매글에 하트가 있는지 없는지 확인
    public boolean getHeartByBoardNo(AddHeartRequest addHeartRequest){
        Optional<Heart> optionalHeart = heartRepository.findByUserUserNoAndNoAndType(
                addHeartRequest.getUserNo()
                , Integer.parseInt(addHeartRequest.getNo())
                , addHeartRequest.getType()
        );

        return optionalHeart.isPresent();
    }

}
