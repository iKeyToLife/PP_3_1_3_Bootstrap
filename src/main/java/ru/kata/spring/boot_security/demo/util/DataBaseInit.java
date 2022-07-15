package ru.kata.spring.boot_security.demo.util;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;

@Component
public class DataBaseInit {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public DataBaseInit(UserService userService, RoleService roleService){
        this.userService = userService;
        this.roleService = roleService;
    }

    @PostConstruct
    private void postConstruct() {
        roleService.save(new Role("ROLE_ADMIN"));
        roleService.save(new Role("ROLE_USER"));

        String[] rolesAdmin = {"ROLE_ADMIN", "ROLE_USER"};
        userService.save(new User("admin", "admin", 20, "admin"), rolesAdmin, "admin");

        String[] rolesUser = {"ROLE_USER"};
        userService.save(new User("user", "user", 20, "user"), rolesUser, "admin");
    }
}
