package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

//Помечаем что это класс контроллера для работы с thymeleaf
@Controller
//@RequestMapping помечает общий URL-адрес для каждого метода, например /lesson/allUsers
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    //Principal запоминает данные об авторизованном пользователе
    @GetMapping("/user")
    public String showUser(Principal principal, Model model) {
        User user = userService.findByUserName(principal.getName()).orElse(null);
        model.addAttribute("user", user);
        return "user";
    }

}

