package me.whiteship.natual.event;

import lombok.Getter;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Getter
public class EventResource extends ResourceSupport {

    private Event event;

    public EventResource(Event event) {
        this.event = event;
        add(linkTo(EventController.class).withRel("event"));
        add(linkTo(methodOn(EventController.class).getEvent(event.getId())).withSelfRel());
    }
}
