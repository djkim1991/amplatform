package me.whiteship.natual.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.hasValue;
import static org.mockito.Mockito.when;
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

    @MockBean
    EventRepository eventRepository;

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
     *   * CREATED 201
     *   * data
     *     * Event: created event info
     *   * links
     *     * location: link to view the event.
     *     * profile: link to a document that describes this API.
     *
     */
    @Test
    public void createNewEvent() throws Exception {
        // Given
        EventDto.Create eventDto = createEvent();
        Event savedEvent = Event.builder()
                .id(1)
                .eventStatus(EventStatus.DRAFT)
                .offline(false)
                .free(false)
                .build();
        when(eventRepository.save(Mockito.any(Event.class))).thenReturn(savedEvent);

        // When & Then
        mockMvc.perform(post("/events")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(eventDto))
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", Matchers.is(1)))
                .andExpect(jsonPath("$.offline", Matchers.is(false)))
                .andExpect(jsonPath("$.free", Matchers.is(false)))
                .andExpect(jsonPath("$.eventStatus", Matchers.is(EventStatus.DRAFT.toString())))
                .andExpect(jsonPath("$._links.profile.href", Matchers.is("http://localhost:8080/docs/api/events/create-event")))
                .andExpect(jsonPath("$._links.view-event.href", Matchers.is("http://localhost/events/1")))
        ;
    }

    private EventDto.Create createEvent() {
        return EventDto.Create.builder()
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
