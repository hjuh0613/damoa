package com.damoa.damoaPJT.board;

import com.damoa.damoaPJT.board.dto.BoardListResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/board/boardCN")
    public String getBoardCN (Model model) {
        List<BoardListResponse> BoardCN = boardService.findAll();
        model.addAttribute("BoardCN", BoardCN);

        return "/BoardCN";
    }

    @GetMapping("/sale")
    public String sale(Model model) {

        return "/board/sale";
    }

    @GetMapping("/boardCN")
    public String boardCN(Model model) {

        return "/board/boardCN";
    }

    @GetMapping("/boardST")
    public String boardST(Model model) {

        return "/board/boardST";
    }

    @GetMapping("/boardWatch")
    public String boardWatch(Model model) {

        return "/board/boardWatch";
    }

    @GetMapping("/boardSpeaker")
    public String boardSpeaker(Model model) {

        return "/board/boardSpeaker";
    }

    @GetMapping("/boardHome")
    public String boardHome(Model model) {

        return "/board/boardHome";
    }

    @GetMapping("/boardETC")
    public String boardETC(Model model) {

        return "/board/boardETC";
    }

}
