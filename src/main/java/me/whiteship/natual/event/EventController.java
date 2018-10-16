package me.whiteship.natual.event;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity all() {
        throw new UnsupportedOperationException();
    }

    @GetMapping("/{id}")
    public ResponseEntity getEvent(@PathVariable Integer id) {
        throw new UnsupportedOperationException();
    }

    @PostMapping
    public ResponseEntity createEvent(@Valid @RequestBody EventDto.Create eventCreate, BindingResult errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        System.out.println(eventCreate);
        // TODO validation

        Event event = modelMapper.map(eventCreate, Event.class);
        Event newEvent = eventRepository.save(event);
        EventResource eventResource = new EventResource(newEvent);
        return ResponseEntity.created(linkTo(methodOn(this.getClass()).getEvent(newEvent.getId())).toUri())
                .body(eventResource);
    }

    @PutMapping("/{id}")
    public ResponseEntity put(@PathVariable Integer id, Event event) {
        throw new UnsupportedOperationException();
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
