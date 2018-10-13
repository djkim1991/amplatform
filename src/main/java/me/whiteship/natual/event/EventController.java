package me.whiteship.natual.event;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class EventController {

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/events")
    public ResponseEntity createEvent(@Valid @RequestBody EventDto.Create eventCreate, BindingResult errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }

        System.out.println(eventCreate);
        // TODO validation createEvent
        Event event = modelMapper.map(eventCreate, Event.class);
        // TODO save new event
        return ResponseEntity.ok(event);
    }

}