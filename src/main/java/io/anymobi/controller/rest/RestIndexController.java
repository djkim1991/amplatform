package io.anymobi.controller.rest;

import io.anymobi.controller.rest.board.JpaBoardController;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class RestIndexController {

    @GetMapping("/api")
    public ResourceSupport root() {
        ResourceSupport index = new ResourceSupport();
        index.add(linkTo(JpaBoardController.class).withRel("boards"));
        return index;
    }
}
