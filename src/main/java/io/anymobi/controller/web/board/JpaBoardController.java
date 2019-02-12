package io.anymobi.controller.web.board;

import io.anymobi.common.annotation.SocialUser;
import io.anymobi.controller.rest.event.EventController;
import io.anymobi.controller.rest.event.EventResource;
import io.anymobi.domain.entity.board.Board;
import io.anymobi.domain.entity.event.Event;
import io.anymobi.domain.entity.users.User;
import io.anymobi.services.jpa.board.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Controller("webJpaBoardController")
@RequestMapping("/board")
@Slf4j
public class JpaBoardController {
    private final BoardService boardService;

    public JpaBoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping({"", "/"})
    public String getBoard(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        model.addAttribute("board", boardService.findBoardByIdx(idx));
        return "/board/form";
    }

    @GetMapping("/list")
    public String list(@PageableDefault Pageable pageable, PagedResourcesAssembler<Board> assembler, @SocialUser User user, Model model) {

        if (user == null) {
            log.debug("Current user doesn't exist");
        } else {
            log.debug("Current user {} {}", user.getId(), user.getEmail());
        }

        Page<Board> boardList = boardService.findBoardList(pageable);
        PagedResources<BoardResource> resources = assembler.toResource(boardList, entity -> new BoardResource(entity));
        resources.add(linkTo(JpaBoardController.class).withRel("boards"));
     //   resources.add(linkTo(methodOn(JpaBoardController.class).getBoard(null, null)).withRel("get-an-board"));

        if (user != null) {
            resources.add(linkTo(methodOn(io.anymobi.controller.rest.board.JpaBoardController.class).createBoard(null)).withRel("create-new-board"));
        }
        resources.add(linkToProfile("resources-boards-list"));

        model.addAttribute("boardList", boardList);
        model.addAttribute("resources", resources);

        return "/board/list";
    }

    private Link linkToProfile(String anchor) {
        String linkValue = "</docs/index.html>; rel=\"profile\";";
        if (anchor != null) {
            linkValue = "</docs/index.html#" + anchor + ">; rel=\"profile\";";
        }
        return Link.valueOf(linkValue);
    }

    private Link linkToUpdate(Event newEvent) {
        return linkTo(methodOn(EventController.class).update(newEvent.getId(), null, null, null)).withRel("update");
    }

}
