package io.anymobi.controller.rest.board;

import io.anymobi.common.hateoas.HateoasHolder;
import io.anymobi.common.hateoas.resources.BoardResource;
import io.anymobi.domain.dto.board.BoardDto;
import io.anymobi.domain.entity.board.Board;
import io.anymobi.domain.entity.users.User;
import io.anymobi.services.jpa.board.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/api")
@Slf4j
public class JpaBoardController {

    private final BoardService boardService;
    private final ModelMapper modelMapper;

    public JpaBoardController(BoardService boardService, ModelMapper modelMapper) {
        this.boardService = boardService;
        this.modelMapper = modelMapper;
    }

    @GetMapping(value = "/boards", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity list(@PageableDefault Pageable pageable,
                               PagedResourcesAssembler<Board> assembler,
                               @AuthenticationPrincipal User user) {

        if (user == null) {
            log.debug("Current user doesn't exist");
        } else {
            log.debug("Current user {} {}", user.getId(), user.getEmail());
        }

        Page<Board> boardList = boardService.findBoardList(pageable);
        PagedResources<BoardResource> resources = assembler.toResource(boardList, entity -> new BoardResource(entity));
        resources.add(linkTo(this.getClass()).withRel("boards"));
        resources.add(linkTo(methodOn(this.getClass()).getBoard(null,null)).withRel("get-an-board"));

        if (user != null) {
            resources.add(linkTo(methodOn(this.getClass()).createBoard(null, null, null)).withRel("create-new-board"));
        }
        resources.add(HateoasHolder.linkToProfile("resources-boards-list"));

        return ResponseEntity.ok().body(resources);
    }

    @GetMapping(value = "/boards/{idx}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getBoard(@PathVariable Long idx,
                                   @AuthenticationPrincipal User user) {

        Board board = boardService.findBoardByIdx(idx);
        if (board == null) {
            return HateoasHolder.notFoundResponse();
        }

        BoardResource boardResource = new BoardResource(board);
        if (user != null) {
            boardResource.add(linkToUpdate(board));
        }
        boardResource.add(HateoasHolder.linkToProfile("resources-boards-get"));

        return ResponseEntity.ok().body(boardResource);
    }

    @PostMapping(value = "/boards", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity createBoard(@Valid @RequestBody BoardDto boardDto,
                                      BindingResult errors,
                                      @AuthenticationPrincipal User user) {

        if (errors.hasErrors()) {
            return HateoasHolder.badRequestResponse(errors);
        }

        Board board = modelMapper.map(boardDto, Board.class);
        board.setUser(user);
        board.setCreatedDate(LocalDateTime.now());
        board.setUpdatedDate(LocalDateTime.now());
        Board newBoard = boardService.save(board);

        BoardResource boardResource = new BoardResource(newBoard);
        boardResource.add(HateoasHolder.linkToProfile("resources-boards-create"));
        boardResource.add(linkToUpdate(newBoard));

        URI newBoardLocation = linkTo(methodOn(this.getClass()).getBoard(newBoard.getIdx(),null)).toUri();
        return ResponseEntity.created(newBoardLocation).body(boardResource);
    }

    @PutMapping(value = "/boards/{idx}", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity update(@PathVariable Long idx,
                                 @Valid @RequestBody BoardDto boardDto,
                                 BindingResult errors,
                                 @AuthenticationPrincipal User user) {

        if (errors.hasErrors()) {
            return HateoasHolder.badRequestResponse(errors);
        }

        Board board = this.boardService.findBoardByIdx(idx);
        if (board == null) {
            return HateoasHolder.notFoundResponse();
        }

        if (user == null) {
            return new ResponseEntity(HttpStatus.FORBIDDEN);
        }

        modelMapper.map(boardDto, board);
        board.setUpdatedDate(LocalDateTime.now());
        Board saveBoard = boardService.save(board);

        BoardResource boardResource = new BoardResource(saveBoard);
        boardResource.add(HateoasHolder.linkToProfile("resources-boards-update"));

        return ResponseEntity.ok().body(boardResource);
    }

    @DeleteMapping(value = "/boards/{idx}")
    public ResponseEntity delete(@PathVariable Long idx) {

        boardService.delete(idx);
        return ResponseEntity.ok().body("{\"state\":\"success\"}");
    }

    public Link linkToUpdate(Board newBoard) {
        return linkTo(methodOn(JpaBoardController.class).update(newBoard.getIdx(), null, null, null)).withRel("update");
    }

}
