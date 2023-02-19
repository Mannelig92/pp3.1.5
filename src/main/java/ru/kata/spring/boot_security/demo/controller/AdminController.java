package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Controller
@RequestMapping("/lesson/admin")
public class AdminController {
    private UserServiceImpl userService;
    private RoleServiceImpl roleService;

    @Autowired
    public AdminController(UserServiceImpl userService, RoleServiceImpl roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String showAllUsers(Principal principal, Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("admin", userService.getAllUsers());
        model.addAttribute("roles", roleService.findAll());
        model.addAttribute("thisUser", userService.findByUserName(principal.getName()).get());
        return "admin";
    }
    @PostMapping()
    public String save(@ModelAttribute("user") User user) {
            userService.saveUser(user);
            return "redirect:/lesson/admin";
    }
    //получение юзера по id. вместо id можно будет поместить число и с помощью аннотации PathVariable
    //мы извлёчём этот id из url и получим к нему доступ
    //PathVariable предоставит этот id

//    @GetMapping(value = "/{id}/edit")
//    public String editUser(@PathVariable("id") long id, Model model) {
//        model.addAttribute("user", userService.getUser(id));
//        model.addAttribute("roles", roleServiceImpl.findAll());
//        return "edit";
//    }

    @PatchMapping(value = "/{id}")
    public String update(@ModelAttribute("user") User user) {
        userService.editUser(user);
        return "redirect:/lesson/admin";
    }

    @DeleteMapping(value = "/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.removeUserById(id);
        return "redirect:/lesson/admin";
    }

//    @GetMapping(value = "/{id}/showUser")
//    public String showUser(@PathVariable("id") long id, Model model) {
//        model.addAttribute("user", userService.getUser(id));
//        return "showUser";
//    }
}
