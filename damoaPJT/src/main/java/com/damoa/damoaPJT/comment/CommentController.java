package com.damoa.damoaPJT.comment;

import com.damoa.damoaPJT.comment.dto.AddCommentRequest;
import com.damoa.damoaPJT.comment.dto.CommentListResponse;
import com.damoa.damoaPJT.comment.dto.UpdateCommentRequest;
import com.damoa.damoaPJT.entity.Comment;
import com.damoa.damoaPJT.user.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CommentController {

    private final CommentService commentService;

    // html이 데이터를 요청하는건 controller
    // controller는 다시 html에 데이터를 반환

    // List<CommentListResponse> 는 반환 타입
    @GetMapping("/commentList")
    @ResponseBody // 비동기 할 때는 무조건 넣어야함
    public List<CommentListResponse> getCommentListByReviewNo (@RequestParam("review_no") int reviewNo, Model model) {
        return commentService.getCommentByReviewNo(reviewNo);
    }

    @PostMapping("/addComment")
    @ResponseBody
    public Integer addComment (@AuthenticationPrincipal CustomUserDetails user, @RequestBody AddCommentRequest addCommentRequest) {

        addCommentRequest.setUserNo(user.getUserNo());

        return commentService.addComment(addCommentRequest);
    }

    @PostMapping("/deleteComment")
    @ResponseBody
    public Integer updateComment (@RequestBody UpdateCommentRequest updateCommentRequest) {

        return  commentService.updateComment(updateCommentRequest).getCommentNo();
    }


}
