package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
public class MainController {
    private final UserService userService;

    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/user")
    public String user(ModelMap modelMap, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        modelMap.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/admin")
    public String admin(Model model, Principal principal) {
        User user = userService.findByEmail(principal.getName());
        User userNew = new User();
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("user", user);
        model.addAttribute("userNew", userNew);
        return "admin";
    }

    @PostMapping("/admin/save")
    public String create(@ModelAttribute("user") User user,
                         @RequestParam(value = "rolesList") String[] roles,
                         @ModelAttribute("password") String password) {
        userService.save(user, roles, password);
        return "redirect:/admin";
    }

    @PutMapping("/admin/{id}/update")
    public String update (@ModelAttribute("user") User user, @PathVariable("id") int id,
                          @RequestParam(value = "rolesList") String [] roles,
                          @ModelAttribute("pass") String pass) {
        userService.update(user, id, roles, pass);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}/delete")
    public String delete (@PathVariable("id") long id) {
        userService.delete(id);
        return "redirect:/admin";
    }
}
