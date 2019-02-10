package io.anymobi.controller.web.board;

import io.anymobi.common.annotation.SocialUser;
import io.anymobi.domain.entity.users.User;
import io.anymobi.services.jpa.board.BoardService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/board")
public class JpaBoardController {
    private final BoardService boardService;

    public JpaBoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping({"", "/"})
    public String board(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        model.addAttribute("board", boardService.findBoardByIdx(idx));
        return "/board/form";
    }

    @GetMapping("/list")
    public String list(@PageableDefault Pageable pageable, @SocialUser User user, Model model) {
        model.addAttribute("boardList", boardService.findBoardList(pageable));
        return "/board/list";
    }

}
