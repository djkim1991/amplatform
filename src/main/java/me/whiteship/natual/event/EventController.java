package me.whiteship.natual.event;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceSupport;
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

        EventResource eventResource = new EventResource(byId.get());
        // TODO add links per roles
        return ResponseEntity.ok().body(eventResource);
    }

    @PostMapping
    public ResponseEntity createEvent(@Valid @RequestBody EventDto.CreateOrUpdate eventCreate, BindingResult errors) {
        if (errors.hasErrors()) {
            // TODO convert error to body
            return ResponseEntity.badRequest().build();
        }

        // TODO domain validation

        Event event = modelMapper.map(eventCreate, Event.class);
        event.update();
        Event newEvent = eventRepository.save(event);
        EventResource eventResource = new EventResource(newEvent);
        return ResponseEntity.created(linkTo(methodOn(this.getClass()).getEvent(newEvent.getId())).toUri())
                .body(eventResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity put(@PathVariable Integer id, @Valid @RequestBody EventDto.CreateOrUpdate eventDto, BindingResult errors) {
        if (errors.hasErrors()) {
            // TODO convert error to body
            return ResponseEntity.badRequest().build();
        }

        Optional<Event> byId = this.eventRepository.findById(id);
        if (!byId.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Event event = byId.get();
        modelMapper.map(eventDto, event);
        event.update();
        // TODO domain validation

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
