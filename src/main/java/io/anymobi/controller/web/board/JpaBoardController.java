package io.anymobi.controller.web.board;

import io.anymobi.common.annotation.SocialUser;
import io.anymobi.domain.entity.board.Board;
import io.anymobi.domain.entity.users.User;
import io.anymobi.services.jpa.board.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller("webJpaBoardController")
@RequestMapping("/board")
@Slf4j
public class JpaBoardController {
    private final BoardService boardService;

    public JpaBoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping(value = {"", "/"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public String getBoard(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        model.addAttribute("board", boardService.findBoardByIdx(idx));
        return "board/form";
    }

    @GetMapping("/list")
    public String list(@PageableDefault Pageable pageable, PagedResourcesAssembler<Board> assembler, @SocialUser User user, Model model) {

        Page<Board> boardList = boardService.findBoardList(pageable);
        model.addAttribute("boardList", boardList);
        return "board/list";
    }
}
