package io.anymobi.common.hateoas;

import io.anymobi.common.hateoas.resources.ErrorResource;
import io.anymobi.controller.rest.board.JpaBoardController;
import io.anymobi.domain.entity.board.Board;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class HateoasHolder {

    public static ResponseEntity<Object> notFoundResponse() {
        return ResponseEntity.notFound().build();
    }

    public static ResponseEntity<ErrorResource> badRequestResponse(BindingResult errors) {
        return ResponseEntity.badRequest().body(new ErrorResource(errors));
    }

    public static Link linkToProfile(String anchor) {
        String linkValue = "</docs/index.html>; rel=\"profile\";";
        if (anchor != null) {
            linkValue = "</docs/index.html#" + anchor + ">; rel=\"profile\";";
        }
        return Link.valueOf(linkValue);
    }

    public static Link linkToUpdate(Board newBoard) {
        return linkTo(methodOn(JpaBoardController.class).update(newBoard.getIdx(), null, null, null)).withRel("update");
    }
}
