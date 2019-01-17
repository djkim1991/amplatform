package io.anymobi.common;

import io.anymobi.controller.rest.RestIndexController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.validation.Errors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

public class ErrorResource extends Resource<Errors> {

    public ErrorResource(Errors content, Link... links) {
        super(content, links);
        add(linkTo(RestIndexController.class).withRel("index"));
    }
}
