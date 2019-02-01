//package io.anymobi.controller.web;
//
//import io.anymobi.services.jpa.security.RoleResourceService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@Controller
//public class HelloController {
//
//    @Autowired
//    private RoleResourceService roleResourceService;
//
//    @GetMapping({"", "/home"})
//    public String home() {
//        return "home";
//    }
//
//    @RequestMapping(value = {"/homepage"})
//    public String homepage() {
//
//        return "homepage";
//    }
//
//    @RequestMapping(value = "/login")
//    public String login() {
//        return "login";
//    }
//
//    @RequestMapping(value = "/denied")
//    public String denied(Model model) {
//        model.addAttribute("roles", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
//        return "denied";
//    }
//
//    @RequestMapping(value = "/users")
//    public String user(Model model) {
//        model.addAttribute("roles", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
//        return "users/users";
//    }
//
//    @RequestMapping(value = "/admin")
//    public String admin(Model model) {
//        model.addAttribute("roles", SecurityContextHolder.getContext().getAuthentication().getAuthorities());
//        return "admin/admin";
//    }
//
//    @RequestMapping(value = "/logout", method = RequestMethod.GET)
//    public String logout(HttpServletRequest request, HttpServletResponse response) {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        if (auth != null) {
//            new SecurityContextLogoutHandler().logout(request, response, auth);
//        }
//        return "redirect:/login";
//    }
//
//
//    @RequestMapping("/delete/{id}")
//    @ResponseBody
//    public String delete(@PathVariable Long id) {
//        roleResourceService.resourcesDelete(id);
//        return "ok";
//    }
//}