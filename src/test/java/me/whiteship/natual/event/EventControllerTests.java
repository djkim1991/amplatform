package me.whiteship.natual.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.whiteship.natual.common.Description;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class EventControllerTests {

    MockMvc mockMvc;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    EventRepository eventRepository;

    @Rule
    public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .apply(documentationConfiguration(this.restDocumentation)
                    .operationPreprocessors()
                        .withRequestDefaults(prettyPrint())
                        .withResponseDefaults(prettyPrint())
                        .and()
//                    .uris()
//                        .withScheme("http")
//                        .withHost("api.whiteship.me")
                )
                .build();
    }

    /**
     * "createCreateEventDto" action creates new event.
     *
     * TODO Constraints: Can be requested only by admin.
     * TODO link to profile
     */
    @Test
    public void createEvent() throws Exception {
        // Given
        EventDto.Create eventDto = createCreateEventDto();
        Event savedEvent = createSampleEvent();
        when(eventRepository.save(Mockito.any(Event.class))).thenReturn(savedEvent);

        // When & Then
        mockMvc.perform(post("/events")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(eventDto))
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.event.id", Matchers.is(1)))
                .andExpect(jsonPath("$.event.offline", Matchers.is(false)))
                .andExpect(jsonPath("$.event.free", Matchers.is(false)))
                .andExpect(jsonPath("$.event.eventStatus", Matchers.is(EventStatus.DRAFT.toString())))
//                .andExpect(jsonPath("$._links.profile.href", Matchers.is("http://localhost:8080/docs/api/events/create-event")))
                .andExpect(jsonPath("$._links.self.href", Matchers.is("http://localhost:8080/events/1")))
                .andDo(document(
                    "create-event",
                    links(halLinks(),
//                            linkWithRel("profile").description("Link to profile"),
                            linkWithRel("self").description("Link to the created event"),
                            linkWithRel("event").description("Link to view all events")),
                    requestFields(
                        fieldWithPath("name").description("event name"),
                        fieldWithPath("description").description("description of the event"),
                        fieldWithPath("beginEnrollmentDateTime").description("date and time to begin enrollment."),
                        fieldWithPath("closeEnrollmentDateTime").description("date and time to close enrollment."),
                        fieldWithPath("beginEventDateTime").description("date and time to begin the event."),
                        fieldWithPath("endEventDateTime").description("date and time to end the event."),
                        fieldWithPath("location").description("link to the place where the event hold"),
                        fieldWithPath("basePrice").description("optional, price of ticket to enroll."),
                        fieldWithPath("maxPrice").description("optional, maximum price of ticket to enroll. \n" +
                                "if this value does not provided, " +
                                "then it means non-limited bidding will happen,\n" +
                                "and can't expect how much it would be to enroll the event eventually.\n" +
                                "If both basePrice and maxPrice are null or 0, it means free event."),
                        fieldWithPath("limitOfEnrollment").description("number of limit")
                    ),
                    relaxedResponseFields(
                        fieldWithPath("event").description("new event")
                    ),
                    responseHeaders(
                        headerWithName("location").description("new event URL")
                    )
                ))
        ;
    }

    private Event createSampleEvent() {
        return Event.builder()
                    .id(1)
                    .eventStatus(EventStatus.DRAFT)
                    .offline(false)
                    .free(false)
                    .build();
    }

    private EventDto.Create createCreateEventDto() {
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

    @Description("Getting an event successfully.")
    @Test
    public void getEvent() throws Exception {
        // Given
        int existingId = 1;
        Mockito.when(eventRepository.findById(existingId)).thenReturn(Optional.of(this.createSampleEvent()));

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/events/{id}", existingId))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-event",
                    pathParameters(
                        parameterWithName("id").description("identifier of an Event.")
                    ),
                    relaxedResponseFields(
                        fieldWithPath("event").description("Event with the id")
                    )
                ))
        ;
    }

    @Description("Trying to get non-existing event.")
    @Test
    public void getEventFail() throws Exception {
        // Given
        int noneExistingId = 1;
        Mockito.when(eventRepository.findById(noneExistingId)).thenReturn(Optional.empty());

        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/event/{id}", noneExistingId))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andDo(document("get-event-fail",
                    pathParameters(
                            parameterWithName("id").description("identifier of an Event.")
                    )
                ))
        ;
    }

}
