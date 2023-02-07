package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("/lesson/admin")
public class AdminController {
    private UserService userService;
    private RoleServiceImpl roleServiceImpl;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public AdminController(UserService userService, RoleServiceImpl roleServiceImpl, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleServiceImpl = roleServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping()
    public String showAllUsers(Model model) {
        model.addAttribute("admin", userService.getAllUsers());
        model.addAttribute("roles", roleServiceImpl.findAll());
        return "admin";
    }

    //получение юзера по id. вместо id можно будет поместить число и с помощью аннотации PathVariable
    //мы извлёчём этот id из url и получим к нему доступ
    //PathVariable предоставит этот id
    @GetMapping(value = "/{id}/edit")
    public String editUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        model.addAttribute("roles", roleServiceImpl.findAll());
        return "edit";
    }

    @PatchMapping(value = "/{id}")
    public String update(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/edit";
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.editUser(user);
        return "redirect:/lesson/admin";
    }

    @DeleteMapping(value = "/{id}/delete")
    public String delete(@PathVariable("id") long id) {
        userService.removeUserById(id);
        return "redirect:/lesson/admin";
    }

    @GetMapping(value = "/{id}/showUser")
    public String showUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "showUser";
    }
}
