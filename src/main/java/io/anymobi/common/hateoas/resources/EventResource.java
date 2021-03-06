package io.anymobi.common.hateoas.resources;

import io.anymobi.controller.rest.event.EventController;
import io.anymobi.domain.entity.event.Event;
import lombok.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class EventResource extends Resource<Event> {

    public EventResource(Event event, Link... links) {
        super(event, links);
        add(linkTo(EventController.class).withRel("event"));
        add(linkTo(methodOn(EventController.class).getEvent(event.getId(), null)).withSelfRel());
    }

}