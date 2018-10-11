package me.whiteship.natual.event;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest
public class EventApiTests {

    @Autowired
    MockMvc mockMvc;

    /**
     * "createNewEvent" action creates new event.
     *
     * Constraints: Can be requested only by admin.
     * Input:
     *   * name: event name
     *   * description: description of the event
     *   * enrollBeginDateTime: date and time to begin enrollment.
     *   * closingEnrollmentDateTime: date and time to close enrollment.
     *   * beginDateTime: date and time to begin the event.
     *   * basePrice: price of ticket to enroll.
     *   * maxPrice(optional): maximum price of ticket to enroll,
     *      if this value does not provided, then it means non-limited bidding will happen,
     *      and can't expect how much it would be to enroll the event eventually.
     * Output:
     *   * data
     *     * id: id of the created event
     *   * links
     *     * self: can get the created event resource.
     *     * cancelEvent: will cancel an event, only before the enrollment started
     *     * viewAvailableEvents: will show events only can enroll or already enrolled.
     *     * viewEnrolledEvents: will show all enrolled events. (including end events)
     */
    @Test
    public void createNewEvent() {

    }



}
