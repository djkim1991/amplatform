package me.whiteship.natual.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.hasValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class EventControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    /**
     * "createNewEvent" action creates new event.
     *
     * Constraints: Can be requested only by admin.
     *
     * Input:
     *   * name: event name
     *   * description: description of the event
     *   * beginEnrollmentDateTime: date and time to begin enrollment.
     *   * closeEnrollmentDateTime: date and time to close enrollment.
     *   * beginEventDateTime: date and time to begin the event.
     *   * endEventDateTime
     *   * basePrice(optional): price of ticket to enroll.
     *   * maxPrice(optional): maximum price of ticket to enroll,
     *      if this value does not provided, then it means non-limited bidding will happen,
     *      and can't expect how much it would be to enroll the event eventually.
     *      If both basePrice and maxPrice are null or 0, it means free event.
     *   * location
     *   * limitOfEnrollment
     *   
     * Output:
     *   * data
     *     * id: id of the created event
     *   * links
     *     * self: can get the created event resource.
     *     * cancelEvent: cancel this event, can be done only before the enrollment is started.
     *     * viewAvailableEvents: show events that can enroll including already enrolled event.
     *     * viewEnrolledEvents: show all enrolled events.
     *     * viewAttendedEvents: show attended events.
     *     * viewEndedEvents: show already ended events.
     *
     */
    @Test
    public void createNewEvent() throws Exception {
        EventDto.Create event = EventDto.Create.builder()
                .name("new event")
                .description("test")
                .beginEnrollmentDateTime(LocalDateTime.of(2018, 10, 15, 0, 0))
                .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 3, 23, 59))
                .beginEventDateTime(LocalDateTime.of(2018, 11, 10, 9, 0))
                .endEventDateTime(LocalDateTime.of(2018, 11, 10, 14, 0))
                .location("신촌 토즈")
                .basePrice(50000)
                .maxPrice(10000)
                .build();

        mockMvc.perform(post("/events")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
//                .andExpect(jsonPath("$.id", hasValue(any(Integer.class))));
    }

    /**
     * check if the validation annotations work when it gets wrong data.
     * TODO content of bad request needs to explain why it is bad.
     */
    @Test
    public void createNewEvent_bindingError() throws Exception {
        Event event = Event.builder().build();

        mockMvc.perform(post("/events")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(event)))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }



}
