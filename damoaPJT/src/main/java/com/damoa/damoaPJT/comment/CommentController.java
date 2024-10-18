package com.damoa.damoaPJT.comment;

import com.damoa.damoaPJT.comment.dto.CommentListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/commentList")
    public List<CommentListResponse> getCommentListByReviewNo (@RequestParam("review_no") int reviewNo, Model model) {
        return commentService.getCommentByReviewNo(reviewNo);
    }
}
