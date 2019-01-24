package io.anymobi.controller.rest.users;

import io.anymobi.domain.entity.sec.User;
import lombok.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class UserResource extends Resource<User> {

    public UserResource(User user, Link... links) {
        super(user, links);
        add(linkTo(UserController.class).withRel("User"));
        add(linkTo(methodOn(UserController.class).getUser(user.getId(), null)).withSelfRel());
    }

}
