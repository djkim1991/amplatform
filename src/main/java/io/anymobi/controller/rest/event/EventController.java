package io.anymobi.controller.rest.event;


import io.anymobi.common.annotation.CurrentUser;
import io.anymobi.common.hateoas.resources.ErrorResource;
import io.anymobi.common.hateoas.resources.EventResource;
import io.anymobi.common.validator.EventValidator;
import io.anymobi.domain.dto.event.EventDto;
import io.anymobi.domain.entity.event.Event;
import io.anymobi.domain.entity.users.User;
import io.anymobi.repositories.jpa.event.EventRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/events")
@Slf4j
public class EventController {

    private final ModelMapper modelMapper;

    private final EventRepository eventRepository;

    private final EventValidator eventValidator;

    public EventController(ModelMapper modelMapper, EventRepository eventRepository, EventValidator eventValidator) {
        this.modelMapper = modelMapper;
        this.eventRepository = eventRepository;
        this.eventValidator = eventValidator;
    }

    @GetMapping
    public ResponseEntity all(Pageable pageable, PagedResourcesAssembler<Event> assembler, User currentUser) {
        if (currentUser == null) {
            log.debug("Current user doesn't exist");
        } else {
            log.debug("Current user {} {}", currentUser.getId(), currentUser.getEmail());
        }

        Page<Event> events = eventRepository.findAll(pageable);
        PagedResources<EventResource> resources = assembler.toResource(events, entity -> new EventResource(entity));
        resources.add(linkTo(EventController.class).withRel("events"));
        resources.add(linkTo(methodOn(EventController.class).getEvent(null, null)).withRel("get-an-event"));
        if (currentUser != null && currentUser.getId() != null) {
            resources.add(linkTo(methodOn(EventController.class).createEvent(null, null, null)).withRel("create-new-event"));
        }
        resources.add(linkToProfile("resources-events-list"));
        return ResponseEntity.ok().body(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity getEvent(@PathVariable Integer id, User currentUser) {
        Optional<Event> byId = eventRepository.findById(id);
        if (!byId.isPresent()) {
            return notFoundResponse();
        }

        Event event = byId.get();
        EventResource eventResource = new EventResource(event);
//        if (currentUser != null && currentUser.equals(event.getManager())) {
//            eventResource.add(linkToUpdate(event));
//        }
        eventResource.add(linkToProfile("resources-events-get"));
        return ResponseEntity.ok().body(eventResource);
    }

    @PostMapping
    public ResponseEntity createEvent(@Valid @RequestBody EventDto.CreateOrUpdate eventCreate,
                                      BindingResult errors,
                                      User currentUser) {
        if (errors.hasErrors()) {
            return badRequestResponse(errors);
        }

        Event event = modelMapper.map(eventCreate, Event.class);
        event.update(currentUser);

        eventValidator.validate(event, errors);
        if (errors.hasErrors()) {
            return badRequestResponse(errors);
        }

        Event newEvent = eventRepository.save(event);
        EventResource eventResource = new EventResource(newEvent);
        eventResource.add(linkToProfile("resources-events-create"));
        eventResource.add(linkToUpdate(newEvent));

        URI newEventLocation = linkTo(methodOn(this.getClass()).getEvent(newEvent.getId(), null)).toUri();
        return ResponseEntity.created(newEventLocation).body(eventResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Integer id,
                                 @Valid @RequestBody EventDto.CreateOrUpdate eventDto,
                                 BindingResult errors,
                                 User currentUser) {
        if (errors.hasErrors()) {
            return badRequestResponse(errors);
        }

        Optional<Event> byId = this.eventRepository.findById(id);
        if (!byId.isPresent()) {
            return notFoundResponse();
        }

        Event event = byId.get();
//        if (currentUser != null && !currentUser.equals(event.getManager())) {
//            return new ResponseEntity(HttpStatus.FORBIDDEN);
//        }

        modelMapper.map(eventDto, event);
        event.update();

        eventValidator.validate(event, errors);
        if (errors.hasErrors()) {
            return badRequestResponse(errors);
        }

        EventResource eventResource = new EventResource(event);
        eventResource.add(linkToProfile("resources-events-update"));
        return ResponseEntity.ok().body(eventResource);
    }

    @DeleteMapping("/{id")
    public ResponseEntity delete(@PathVariable Integer id) {
        throw new UnsupportedOperationException();
    }

    @PostMapping("/{id}/publish")
    public ResponseEntity publish(@PathVariable Integer id) {
        throw new UnsupportedOperationException();
    }

    private ResponseEntity<Object> notFoundResponse() {
        return ResponseEntity.notFound().build();
    }

    private ResponseEntity<ErrorResource> badRequestResponse(BindingResult errors) {
        return ResponseEntity.badRequest().body(new ErrorResource(errors));
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
