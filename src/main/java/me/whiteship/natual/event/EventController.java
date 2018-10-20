package me.whiteship.natual.event;

import me.whiteship.natual.common.ErrorResource;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/events")
public class EventController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    EventValidator eventValidator;

    @GetMapping
    public ResponseEntity all(Pageable pageable, PagedResourcesAssembler<Event> assembler) {
        Page<Event> events = eventRepository.findAll(pageable);
        if (events.hasContent()) {
            PagedResources resources = assembler.toResource(events, entity -> new EventResource(entity));
            return ResponseEntity.ok().body(resources);
        }

        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity getEvent(@PathVariable Integer id) {
        Optional<Event> byId = eventRepository.findById(id);
        if (!byId.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Event event = byId.get();
        EventResource eventResource = new EventResource(event);
        eventResource.add(linkTo(methodOn(EventController.class).update(event.getId(), null, null)).withRel("update"));
        // TODO add links per roles
        return ResponseEntity.ok().body(eventResource);
    }

    @PostMapping
    public ResponseEntity createEvent(@Valid @RequestBody EventDto.CreateOrUpdate eventCreate, BindingResult errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(new ErrorResource(errors));
        }

        Event event = modelMapper.map(eventCreate, Event.class);
        event.update();

        eventValidator.validate(event, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(new ErrorResource(errors));
        }

        Event newEvent = eventRepository.save(event);
        EventResource eventResource = new EventResource(newEvent);
        return ResponseEntity.created(linkTo(methodOn(this.getClass()).getEvent(newEvent.getId())).toUri())
                .body(eventResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Integer id, @Valid @RequestBody EventDto.CreateOrUpdate eventDto, BindingResult errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(new ErrorResource(errors));
        }

        Optional<Event> byId = this.eventRepository.findById(id);
        if (!byId.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Event event = byId.get();
        modelMapper.map(eventDto, event);
        event.update();

        eventValidator.validate(event, errors);
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(new ErrorResource(errors));
        }

        EventResource eventResource = new EventResource(event);
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
}
