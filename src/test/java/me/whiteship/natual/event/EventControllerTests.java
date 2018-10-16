package me.whiteship.natual.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.whiteship.natual.common.Description;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.restdocs.RestDocsMockMvcConfigurationCustomizer;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentationConfigurer;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
public class EventControllerTests {

    @TestConfiguration
    static class TestConfig implements RestDocsMockMvcConfigurationCustomizer {
        @Override
        public void customize(MockMvcRestDocumentationConfigurer configurer) {
            configurer.operationPreprocessors()
                    .withResponseDefaults(prettyPrint())
                    .withRequestDefaults(prettyPrint());
        }
    }

    @Autowired
    MockMvc mockMvc;

    @Autowired
    WebApplicationContext wac;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EventRepository eventRepository;

    @Autowired
    ModelMapper modelMapper;

    /**
     * "createCreateEventDto" action creates new event.
     *
     * TODO Constraints: Can be requested only by admin.
     * TODO link to profile
     */
    @Description("Trying to create new event with correct data.")
    @Test
    public void createEvent() throws Exception {
        // Given
        EventDto.Create eventDto = createCreateEventDto();

        // When & Then
        mockMvc.perform(post("/events")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(objectMapper.writeValueAsString(eventDto))
                    .accept(MediaType.APPLICATION_JSON_UTF8))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.event.id", Matchers.notNullValue()))
                .andExpect(jsonPath("$.event.offline", Matchers.is(false)))
                .andExpect(jsonPath("$.event.free", Matchers.is(false)))
                .andExpect(jsonPath("$.event.eventStatus", Matchers.is(EventStatus.DRAFT.toString())))
//                .andExpect(jsonPath("$._links.profile.href", Matchers.is("http://localhost:8080/docs/api/events/create-event")))
                .andExpect(jsonPath("$._links.self.href", Matchers.containsString("/events/")))
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
        return modelMapper.map(this.createCreateEventDto(), Event.class);
    }

    private EventDto.Create createCreateEventDto() {
        return EventDto.Create.builder()
                    .name("test event")
                    .description("testing event apis")
                    .beginEnrollmentDateTime(LocalDateTime.of(2018, 10, 15, 0, 0))
                    .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 3, 23, 59))
                    .beginEventDateTime(LocalDateTime.of(2018, 11, 10, 9, 0))
                    .endEventDateTime(LocalDateTime.of(2018, 11, 10, 14, 0))
                    .location("Inflean")
                    .basePrice(50000)
                    .maxPrice(10000)
                    .build();
    }

    /**
     * TODO content of bad request needs to explain why it is bad.
     */
    @Description("Trying to create an event with wrong data and fail.")
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
        Event newEvent = this.eventRepository.save(this.createSampleEvent());

        // When & Then
        this.mockMvc.perform(RestDocumentationRequestBuilders.get("/events/{id}", newEvent.getId()))
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

    @Description("Trying to get all events.")
    @Test
    public void getEvents() throws Exception {
        // Given
        this.eventRepository.save(this.createSampleEvent());

        // When & Then
        this.mockMvc.perform(get("/events"))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("get-events",
                    requestParameters(
                        parameterWithName("page").description("page to retrieve, begin with and default is 0").optional(),
                        parameterWithName("size").description("Sie of the page to retrieve, default 20").optional()
                    ),
                    relaxedResponseFields(
                        fieldWithPath("page.number").type(JsonFieldType.NUMBER).description("The number of this page."),
                        fieldWithPath("page.size").type(JsonFieldType.NUMBER).description("The size of this page."),
                        fieldWithPath("page.totalPages").type(JsonFieldType.NUMBER).description("The total number of pages."),
                        fieldWithPath("page.totalElements").type(JsonFieldType.NUMBER).description("The total number of results.")
                    )
                ))
        ;
    }

}
