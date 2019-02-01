package io.anymobi.controller.rest.security;

import io.anymobi.services.jpa.security.RoleResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class SecurityController {

    @Autowired
    private RoleResourceService roleResourceService;

    @GetMapping("/facebook")
    public String facebook() {
        return "facebook";
    }

    @GetMapping("/google")
    public String google() {
        return "google";
    }

    @GetMapping("/kakao")
    public String kakao() {
        return "kakao";
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    public String delete(@PathVariable Long id) {
        roleResourceService.resourcesDelete(id);
        return "ok";
    }
}
