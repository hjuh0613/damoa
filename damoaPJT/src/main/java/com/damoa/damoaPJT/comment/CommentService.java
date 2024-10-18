package com.damoa.damoaPJT.comment;

import com.damoa.damoaPJT.comment.dto.CommentListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    // 특정 후기 게시글의 전체 댓글 조회
    public List<CommentListResponse> getCommentByReviewNo(int reviewNo){
        return commentRepository.findByReviewNoWithUser(reviewNo).stream()
                .map(CommentListResponse::new)
                .toList();
    }

}
