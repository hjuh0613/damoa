package com.damoa.damoaPJT.comment;

import com.damoa.damoaPJT.comment.dto.AddCommentRequest;
import com.damoa.damoaPJT.comment.dto.CommentListResponse;
import com.damoa.damoaPJT.comment.dto.UpdateCommentRequest;
import com.damoa.damoaPJT.entity.Comment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    @Transactional
    public Integer addComment(AddCommentRequest addCommentRequest) {

        Integer commentNo = commentRepository.save(addCommentRequest.toEntity()).getCommentNo();
        
        return commentNo;
    }

    @Transactional
    public CommentListResponse updateComment(UpdateCommentRequest updateCommentRequest) {

        Comment entity = commentRepository.findByCommentNo(updateCommentRequest.getCommentNo())
                .orElseThrow(() -> new RuntimeException("Comment update failed"));

        entity.update(updateCommentRequest.getCommentNo(), updateCommentRequest.getCommentContent());

        return commentRepository.findByCommentNo(entity.getCommentNo())
                .map(CommentListResponse::new)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }

}
