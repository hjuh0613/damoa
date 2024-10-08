package com.damoa.damoaPJT.board;

import com.damoa.damoaPJT.board.dto.BoardAddRequest;
import com.damoa.damoaPJT.board.dto.BoardListResponse;
import com.damoa.damoaPJT.board.dto.BoardUpdateRequest;
import com.damoa.damoaPJT.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

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

    @GetMapping("/boardUpdate")
    public String getUpdateBoard(@RequestParam("board_no") int boardNo, Model model) {

        model.addAttribute("boardUpdate", boardService.findByBoardNo(boardNo));

        return "/board/boardUpdate";
    }

    @PostMapping("/boardUpdate")
    public String putUpdateBoard(@ModelAttribute BoardUpdateRequest boardUpdateRequest, Model model) {

        model.addAttribute("Product", boardService.updateBoard(boardUpdateRequest));

        return "/board/product";
    }

    @GetMapping("/addBoard")
    public String goAddBoard(@RequestParam(value = "category_name", required = false) String categoryName, Model model){
        model.addAttribute("categoryName", categoryName);

        return "board/boardInsert";
    }

    @PostMapping(value = "/addBoard")
    public ResponseEntity addBoard(@ModelAttribute BoardAddRequest boardAddRequest, @RequestParam("img") List<MultipartFile> imgFile, Model model){

        // 파일 업로드 처리 필요
        
        // 게시글 저장 로직
        boardService.addProduct(boardAddRequest, imgFile);

        //게시글 작성 완료 후 게시글 목록으로 이동
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/sale")
    public String sale(Model model) {

        return "/board/sale";
    }

}
