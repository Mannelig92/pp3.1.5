package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

//Помечаем что это класс контроллера для работы с thymeleaf
@Controller
//@RequestMapping помечает общий URL-адрес для каждого метода, например /lesson/allUsers
@RequestMapping("/lesson")
public class UserController {
    private UserServiceImpl userService;

    //Подключаем Service через конструктор. Лучше через него, а не через поле или сэттэр
    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    /*
    Далее для создания методов для сайта используем аннотации CRUD(Create,Read,Update,Delete)
     - Get, Post, Put, Delete, Patch, и другие.
    @GetMapping — Обрабатывает get-запросы.Ничего не меняет на сервере, не добавляет, не изменяет,
     просто получает данные с сервера
     */
    @GetMapping
    public String mainPage() {
        return "mainPage";
    }

    @GetMapping(value = "/newUser")
    public String authentication(@ModelAttribute("user") User user) { //Добавление нового юзера
        return "newUser";
    }

    /*
    @PostMapping-Post-запрос изменяет что-то на сервере, например создаём новую учётную запись,
     делаем пост, загружаем фото
     */
    @PostMapping()
    public String save(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("role", new Role());
        userService.saveUser(user);
        return "redirect:/lesson"; //После выполнения метода переходить по данному url
    }
    //Principal запоминает данные об авторизованном пользователе
    @GetMapping("/showUser")
    public String showUser(Principal principal, Model model) {
        User user = userService.findByUserName(principal.getName()).get();
        model.addAttribute("user", user);
        return "showUser";
    }


//    @GetMapping("/registration")
//    public String registration(){
//        return "registration";
//    }
//    @PostMapping("/registration")
//    public String addUser(User user){
//        userService.findByUserName(user.getUserName());
//        user.setRoles();
//        userService.saveUser(user);
//        return "redirect:/login";
//    }


}
