package com.damoa.damoaPJT.userReview;

import com.damoa.damoaPJT.user.dto.CustomUserDetails;
import com.damoa.damoaPJT.userReview.dto.ReviewAddRequest;
import com.damoa.damoaPJT.userReview.dto.UserReviewUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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

        userReviewService.updateReview(userReviewUpdateRequest);

        return "redirect:/review?review_no=" + userReviewUpdateRequest.getReviewNo();
    }

    @GetMapping("/addReview")
    public String goAddReview(Model model){

        return "/userReview/reviewInsert";
    }

    @PostMapping("/addReview")
    public String addReview(@ModelAttribute ReviewAddRequest reviewAddRequest
            , @RequestParam("img") List<MultipartFile> imgFile
            , @AuthenticationPrincipal CustomUserDetails user,  Model model) throws Exception {

        // 로그인한 유저 no 세팅
        reviewAddRequest.setUserNo(user.getUserNo());

        // 후기 게시글 저장
        userReviewService.addReview(reviewAddRequest, imgFile);

        return "redirect:/userReviewList";
    }

    @GetMapping("/deleteReview")
    public String deleteReview(@RequestParam("review_no") int reviewNo, Model model){

        userReviewService.deleteReview(reviewNo);

        return "redirect:/userReviewList";
    }

}
