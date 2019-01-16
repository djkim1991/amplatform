package io.anymobi.controller.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class SessionController {

    @GetMapping("/session")
    public String getSessionId(HttpSession session) {
        return session.getId();
    }
}
