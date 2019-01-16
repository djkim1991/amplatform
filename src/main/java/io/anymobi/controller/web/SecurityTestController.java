package io.anymobi.controller.web;

import io.anymobi.common.enums.UserRole;
import io.anymobi.domain.dto.security.UserDto;
import io.anymobi.domain.entity.User;
import io.anymobi.services.jpa.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.HashSet;

@Controller
@Slf4j
public class SecurityTestController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/")
    public String index(Model model, Principal principal) {

        //model.addAttribute("message", "You are logged in as onjsdnjs" + principal.getName());

        model.addAttribute("message", "You are logged in as onjsdnjs");
        log.info("Hello World!!");

        return "index";
    }

    @GetMapping("/errortest")
    public String home(Model model, Principal principal) {

        return "errorTest";
    }

    @GetMapping("/users")
    public String users(Model model, Principal principal) {

        return "user";
    }

    @PostMapping("/users")
    public String create(UserDto userDto) {

        /*User user = modelMapper.map(userDto, User.class);
        HashSet<UserRole> roles = new HashSet<>();
        roles.add(UserRole.ADMIN);
        roles.add(UserRole.USER);
        user.setRoles(roles);
        log.debug(user.toString());

        if (userService.isExist(user)) {
            throw new IllegalAccessError("Error");
        }
        userService.save(user);*/
        return "index";
    }
}
