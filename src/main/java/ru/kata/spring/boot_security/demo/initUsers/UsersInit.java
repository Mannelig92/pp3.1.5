package ru.kata.spring.boot_security.demo.initUsers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
public class UsersInit {
    private RoleServiceImpl roleService;
    private UserServiceImpl userService;

    @Autowired
    public UsersInit(RoleServiceImpl roleService, UserServiceImpl userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @PostConstruct //Метод выполняется после внедрения зависимостей для инициализации
    public void init() {
        roleService.save(new Role("ROLE_ADMIN"));
        roleService.save(new Role("ROLE_USER"));
        Role admin = roleService.findRoleByRole("ROLE_ADMIN");
        Role user = roleService.findRoleByRole("ROLE_USER");

        userService.saveUser(new User("Harry", "Potter", 22, "1",
                "Harry@mail.ru", List.of(admin)));
        userService.saveUser(new User("Ron", "Weasley", 21, "1",
                "Ron@mail.ru", List.of(user)));
        userService.saveUser(new User("Hermione", "Granger", 22, "1",
                "Hermione@mail.ru", List.of(admin, user)));
    }
}
