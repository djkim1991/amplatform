package me.whiteship.natural.event;

import lombok.extern.slf4j.Slf4j;
import me.whiteship.natural.common.ErrorResource;
import me.whiteship.natural.user.CurrentUser;
import me.whiteship.natural.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.Link;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/events")
@Slf4j
public class EventController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    EventValidator eventValidator;

    @GetMapping
    public ResponseEntity all(Pageable pageable, PagedResourcesAssembler<Event> assembler, @CurrentUser User currentUser) {
        if (currentUser == null) {
            log.debug("Current user doesn't exist");
        } else {
            log.debug("Current user {} {}", currentUser.getId(), currentUser.getEmail());
        }

        Page<Event> events = eventRepository.findAll(pageable);
        var resources = assembler.toResource(events, entity -> new EventResource(entity));
        resources.add(linkTo(EventController.class).withRel("events"));
        resources.add(linkTo(methodOn(EventController.class).getEvent(null)).withRel("get-an-event"));
        if (currentUser != null) {
            resources.add(linkTo(methodOn(EventController.class).createEvent(null, null)).withRel("create-new-event"));
        }
        resources.add(linkToProfile("resources-events-list"));
        return ResponseEntity.ok().body(resources);
    }

    @GetMapping("/{id}")
    public ResponseEntity getEvent(@PathVariable Integer id) {
        Optional<Event> byId = eventRepository.findById(id);
        if (!byId.isPresent()) {
            return notFoundResponse();
        }

        Event event = byId.get();
        EventResource eventResource = new EventResource(event);
        eventResource.add(linkTo(methodOn(EventController.class).update(event.getId(), null, null)).withRel("update"));
        eventResource.add(linkToProfile("resources-events-get"));
        // TODO add links per roles
        return ResponseEntity.ok().body(eventResource);
    }

    @PostMapping
    public ResponseEntity createEvent(@Valid @RequestBody EventDto.CreateOrUpdate eventCreate, BindingResult errors) {
        if (errors.hasErrors()) {
            return badRequestResponse(errors);
        }

        Event event = modelMapper.map(eventCreate, Event.class);
        event.update();

        eventValidator.validate(event, errors);
        if (errors.hasErrors()) {
            return badRequestResponse(errors);
        }

        Event newEvent = eventRepository.save(event);
        EventResource eventResource = new EventResource(newEvent);
        eventResource.add(linkToProfile("resources-events-create"));
        return ResponseEntity.created(linkTo(methodOn(this.getClass()).getEvent(newEvent.getId())).toUri())
                .body(eventResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Integer id, @Valid @RequestBody EventDto.CreateOrUpdate eventDto, BindingResult errors) {
        if (errors.hasErrors()) {
            return badRequestResponse(errors);
        }

        Optional<Event> byId = this.eventRepository.findById(id);
        if (!byId.isPresent()) {
            return notFoundResponse();
        }

        Event event = byId.get();
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
        var linkValue = "</docs/index.html>; rel=\"profile\";";
        if (anchor != null) {
            linkValue = "</docs/index.html#" + anchor + ">; rel=\"profile\";";
        }
        return Link.valueOf(linkValue);
    }
}
