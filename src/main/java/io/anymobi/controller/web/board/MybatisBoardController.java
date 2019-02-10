package io.anymobi.controller.web.board;

import io.anymobi.domain.dto.board.BoardDto;
import io.anymobi.services.mybatis.board.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * <PRE>
 * 1. author	:	(주)애니모비 시스템 개발본부
 * 2. date		:	2019.01
 * </PRE>
 */
@Controller
@Slf4j
@RequestMapping("/boards")
public class MybatisBoardController {

    @Autowired
    BoardService boardService;

    @GetMapping(value = "/{idx}")
    public String selectBoard(@PathVariable int idx, Model model) {
        model.addAttribute("board", boardService.selectBoard(idx));
        return "/board/form";
    }

    @GetMapping(value = {"","/"})
    public String selectBoardList(BoardDto boardDto, Model model) {
        model.addAttribute("boardList", boardService.selectBoardList(boardDto));
        return "/board/list";
    }

    @PostMapping(value="/")
    public String insertBoard(BoardDto boardDto) {

        boardService.insertBoard(boardDto);
        return "/board/list";

    }

//    @GetMapping(value = "/boards/edit")
//    public String BoardEdit() throws Exception {
//
//        return "board/Board_edit";
//    }

    @PostMapping(value = "/{idx}")
    public String updateBoard(@PathVariable long idx, BoardDto boardDto) {
        boardDto.setIdx(idx);
        boardService.updateBoard(boardDto);
        return "/board/list";
    }
}
