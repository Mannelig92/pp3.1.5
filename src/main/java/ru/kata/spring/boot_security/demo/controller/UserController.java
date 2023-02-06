package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import javax.validation.Valid;
import java.security.Principal;

//Помечаем что это класс контроллера для работы с thymeleaf
@Controller
//@RequestMapping помечает общий URL-адрес для каждого метода, например /lesson/allUsers
@RequestMapping("/lesson")
public class UserController {
    private UserServiceImpl userService;
    private RoleServiceImpl roleService;

    @Autowired
    public UserController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String mainPage() {
        return "mainPage";
    }

    @GetMapping(value = "/newUser")
    public String authentication(Model model) { //Добавление нового юзера
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());
        return "newUser";
    }

    @PostMapping()
    public String save(@Validated @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            System.out.println(bindingResult);
            return "/lesson/newUser";
        }
//        model.addAttribute("roles", roleService.findAll());
        userService.saveUser(user);
        return "redirect:/lesson";
    }

    //Principal запоминает данные об авторизованном пользователе
    @GetMapping("/showUser")
    public String showUser(Principal principal, Model model) {
        User user = userService.findByUserName(principal.getName()).get();
        model.addAttribute("user", user);
        return "showUser";
    }

}
