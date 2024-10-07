package com.damoa.damoaPJT.userReview;

import com.damoa.damoaPJT.userReview.dto.UserReviewUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class UserReviewController {

    private final UserReviewService userReviewService;

    @GetMapping("/userReviewList")
    public String getUserReviewList(Model model) {

        model.addAttribute("userReviewList", userReviewService.findAllUserReview());

        return "/userReview/reviewList";
    }

    @GetMapping("/review")
    public String getReview(@RequestParam("review_no") int reviewNo, Model model) {

        model.addAttribute("Review", userReviewService.getReview(reviewNo));

        return "/userReview/review";
    }

    @GetMapping("/updateReview")
    public String getUpdateReview(@RequestParam("review_no") int reviewNo, Model model) {

        model.addAttribute("reviewUpdate", userReviewService.findByReviewNo(reviewNo));

        return "/userReview/reviewUpdate";
    }

    @PostMapping("/updateReview")
    public String putUpdateReview(@ModelAttribute UserReviewUpdateRequest userReviewUpdateRequest, Model model){

        // 수정 -> 수정된 dto를 받을거야

        model.addAttribute("Review", userReviewService.updateReview(userReviewUpdateRequest));

        return "/userReview/review";
    }
}
