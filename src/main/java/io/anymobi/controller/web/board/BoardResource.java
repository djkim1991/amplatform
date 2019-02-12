package io.anymobi.controller.web.board;

import io.anymobi.domain.entity.board.Board;
import lombok.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class BoardResource extends Resource<Board> {

    public BoardResource(Board board, Link... links) {
        super(board, links);
        add(linkTo(JpaBoardController.class).withRel("board"));
       // add(linkTo(methodOn(JpaBoardController.class).getBoard(board.getIdx(), null)).withSelfRel());
    }

}