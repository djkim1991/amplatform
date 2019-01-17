package io.anymobi.controller.web;

import io.anymobi.domain.dto.security.UserDto;
import io.anymobi.services.jpa.security.UserService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;

@Controller
@Slf4j
public class SecurityTestController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelMapper modelMapper;

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
