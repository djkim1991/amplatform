package io.anymobi.controller.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@Slf4j
public class SessionController {

    @GetMapping("/session")
    public String getSessionId(HttpSession session) {
        log.debug(session.getId());
        return session.getId();
    }
}
