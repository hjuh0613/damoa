package com.damoa.damoaPJT.comment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class CommentController {

    public String getCommentList (@RequestParam("review_no") int reviewNo, Model model) {
        //model.addAttribute("commentList", commentService.findAllComment());

        return "/userReview/reviewList";
    }
}
