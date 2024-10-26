package com.damoa.damoaPJT.controller;

import com.damoa.damoaPJT.board.BoardService;
import com.damoa.damoaPJT.heart.HeartService;
import com.damoa.damoaPJT.userReview.UserReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String getSearch (@RequestParam(value="boardSearchPage", defaultValue="0") int boardSearchPage
            , @RequestParam(value="reviewSearchPage", defaultValue="0") int reviewSearchPage
            , @RequestParam(value="search", defaultValue="") String search, Model model) {

        // 검색어 조회한 판매게시글 리스트
        model.addAttribute("boardSearchList", boardService.getBoardListBySearch(boardSearchPage, search));

        // 검색어 조회한 후기게시글 리스트
        model.addAttribute("reviewSearchList", userReviewService.getUserReviewListBySearch(reviewSearchPage, search));

        // 검색어
        model.addAttribute("search", search);

        // 역대 가격정보
        model.addAttribute("price", boardService.findPriceStatisticsByKeyword(search));

        return "/search/search";
    }

    @GetMapping("chartMake")
    @ResponseBody
    public Map<String, List> getChartMake(@RequestParam("search") String search){

        Map<String, List> rtnMap = new HashMap<>();

        // 통계 기능 관련 x축 데이터
        rtnMap.put("xMonth", boardService.findLastFiveMonths(search));

        // 통계 기능 관련 x축 데이터에 대한 최고가 list
        rtnMap.put("maxMonth", boardService.findMaxPriceByMonth(search));

        // 통계 기능 관련 x축 데이터 대한 평균가 list
        rtnMap.put("avgMonth", boardService.findAveragePriceByMonth(search));

        // 통계 기능 관련 x축 데이터 대한 최저가 list
        rtnMap.put("minMonth", boardService.findMinPriceByMonth(search));

        return rtnMap;
    }

}
