package io.anymobi.controller.rest.board;

import io.anymobi.domain.dto.board.BoardDto;
import io.anymobi.domain.entity.board.Board;
import io.anymobi.domain.entity.users.User;
import io.anymobi.services.jpa.board.BoardService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class JpaBoardController {

    private final BoardService boardService;
    private final ModelMapper modelMapper;

    public JpaBoardController(BoardService boardService, ModelMapper modelMapper) {
        this.boardService = boardService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/boards")
    public ResponseEntity board(@RequestBody BoardDto boardDto) {

        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Board board = modelMapper.map(boardDto, Board.class);
        board.setUser(user);
        boardService.save(board);
        return ResponseEntity.ok().body("success");
    }

}
