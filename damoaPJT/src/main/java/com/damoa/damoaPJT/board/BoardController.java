package com.damoa.damoaPJT.board;

import com.damoa.damoaPJT.board.dto.BoardListResponse;
import com.damoa.damoaPJT.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;
    private final CategoryService categoryService;

    @GetMapping("/boardList")
    public String getBoardList (@RequestParam("category_no") int categoryNo, Model model) {

        model.addAttribute("BoardList", boardService.findByIdBoard(categoryNo));
        model.addAttribute("categoryName", categoryService.getCategoryNameByCategoryNo(categoryNo));

        return "/board/board";
        // html 파일이 있는 경로
    }

    @GetMapping("/product")
    public String getProduct (@RequestParam("board_no") int boardNo, Model model) {

        model.addAttribute("Product", boardService.getProduct(boardNo));

        return "/board/product";
    }

    @GetMapping("/addBoard")
    public String goAddBoard(@RequestParam(value = "category_name", required = false) String categoryName, Model model){
        model.addAttribute("categoryName", categoryName);

        return "board/boardInsert";
    }

    @GetMapping("/sale")
    public String sale(Model model) {

        return "/board/sale";
    }

}
