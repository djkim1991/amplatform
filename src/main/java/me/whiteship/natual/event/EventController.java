package me.whiteship.natual.event;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventController {

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/events")
    public Event createEvent(@RequestBody EventDto.Create eventCreate) {
        System.out.println(eventCreate);
        // TODO validation createEvent
        Event event = modelMapper.map(eventCreate, Event.class);
        // TODO save new event
        return event;
    }

}
