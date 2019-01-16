package io.anymobi.services.jpa;

import io.anymobi.controller.rest.UserRestController;
import io.anymobi.controller.web.MemberController;
import io.anymobi.domain.entity.User;
import lombok.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class UserResource extends Resource<User> {

    public UserResource(User User, Link... links) {
        super(User, links);
        add(linkTo(MemberController.class).withRel("User"));
        add(linkTo(methodOn(UserRestController.class).getUser(User.getId(), null)).withSelfRel());
    }

}
