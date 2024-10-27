package com.damoa.damoaPJT.board;

import com.damoa.damoaPJT.board.dto.BoardAddRequest;
import com.damoa.damoaPJT.board.dto.BoardListResponse;
import com.damoa.damoaPJT.board.dto.BoardUpdateRequest;
import com.damoa.damoaPJT.category.CategoryService;
import com.damoa.damoaPJT.file.FileService;
import com.damoa.damoaPJT.heart.HeartService;
import com.damoa.damoaPJT.heart.dto.AddHeartRequest;
import com.damoa.damoaPJT.user.dto.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    private final CategoryService categoryService;

    private final FileService fileService;

    private final HeartService heartService;

    @GetMapping("/boardList")
    public String getBoardList (@RequestParam(value="page", defaultValue="0") int page,
                                @RequestParam("category_no") int categoryNo, Model model) {


        model.addAttribute("BoardList", boardService.findByIdBoard(page, categoryNo));
        model.addAttribute("categoryNo", categoryNo);
        model.addAttribute("categoryName", categoryService.getCategoryNameByCategoryNo(categoryNo));

        return "/board/board";
        // html 파일이 있는 경로
    }

    @GetMapping("/product")
    public String getProduct (@AuthenticationPrincipal CustomUserDetails user, @RequestParam("board_no") int boardNo, Model model) {

        BoardListResponse boardListResponse = boardService.getProduct(boardNo);

        model.addAttribute("Product", boardListResponse);
        model.addAttribute("fileList", fileService.getFileListByBoardNoAndBoardType(boardNo, boardListResponse.getCategoryNo()));

        String type = "";

        switch (boardListResponse.getCategoryNo()) {
            case 1 -> type = "컴퓨터/노트북";
            case 2 -> type = "스마트폰/태블릿";
            case 3 -> type = "워치";
            case 4 -> type = "음향기기";
            case 5 -> type = "생활가전";
            case 6 -> type = "기타";
            case 7 -> type = "후기게시판";
        }

        String tmpNo = String.valueOf(boardListResponse.getBoardNo());

        AddHeartRequest addHeartRequest = new AddHeartRequest(tmpNo, type, user.getUserNo());

        model.addAttribute("isHeart", heartService.getHeartByBoardNo(addHeartRequest));

        return "/board/product";
    }

    @GetMapping("/boardUpdate")
    public String getUpdateBoard(@RequestParam("board_no") int boardNo, Model model) {

        model.addAttribute("boardUpdate", boardService.findByBoardNo(boardNo));

        return "/board/boardUpdate";
    }

    @PostMapping("/boardUpdate")
    public String putUpdateBoard(@ModelAttribute BoardUpdateRequest boardUpdateRequest, Model model) {

        boardService.updateBoard(boardUpdateRequest);

        return "redirect:/product?board_no=" + boardUpdateRequest.getBoardNo();
    }

    @GetMapping("/addBoard")
    public String goAddBoard(@RequestParam(value = "category_name", required = false) String categoryName, Model model){
        model.addAttribute("categoryName", categoryName);

        return "/board/boardInsert";
    }

    @PostMapping("/addBoard")
    public String addBoard(@ModelAttribute BoardAddRequest boardAddRequest
            , @RequestParam("img") List<MultipartFile> imgFile
            , @AuthenticationPrincipal CustomUserDetails user, Model model) throws Exception {

        // 로그인한 유저 no 세팅
        boardAddRequest.setUserNo(user.getUserNo());

        // 판매 게시글 저장
        boardService.addProduct(boardAddRequest, imgFile);

        //게시글 작성 완료 후 게시글 목록으로 이동
        return "redirect:/boardList?category_no=" + boardAddRequest.getCategoryNo();
    }

    @GetMapping("/sale")
    public String sale(Model model) {

        return "/board/sale";
    }

    @GetMapping("/deleteBoard")
    public String deleteBoard(@RequestParam("board_no") int boardNo, @RequestParam("category_no") int categoryNo, Model model){
        boardService.deleteBoard(boardNo);

        return "redirect:/boardList?category_no="+categoryNo;
    }

}
