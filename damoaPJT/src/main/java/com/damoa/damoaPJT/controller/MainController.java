package com.damoa.damoaPJT.controller;

import com.damoa.damoaPJT.board.BoardService;
import com.damoa.damoaPJT.heart.HeartService;
import com.damoa.damoaPJT.userReview.UserReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final BoardService boardService;

    private final HeartService heartService;

    private final UserReviewService userReviewService;

    @GetMapping("/")
    public String home(Model model) {

        // 찜하기 제일 많은 5개 얻어서 model.addAttribute
        model.addAttribute("mostBoard", heartService.getBoardTop5());
        model.addAttribute("mostReview", heartService.getReviewTop5());

        return "main";
    }

    @GetMapping("/search")
    public String getSearch (@RequestParam(value="page", defaultValue="0") int page, @RequestParam(value="search", defaultValue="") String search, Model model) {

        // 검색어 조회한 판매게시글 리스트
        model.addAttribute("boardSearchList", boardService.getBoardListBySearch(page, search));

        // 검색어 조회한 후기게시글 리스트
        model.addAttribute("reviewSearchList", userReviewService.getUserReviewListBySearch(page, search));
        
        return "/search/search";
    }

}
