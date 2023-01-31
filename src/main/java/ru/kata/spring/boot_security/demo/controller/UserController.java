package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

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
    public String save(@ModelAttribute("user") User user) {
        userService.saveUser(user);
        return "redirect:/lesson/allUsers"; //После выполнения метода переходить по данному url
    }

    @GetMapping(value = "/{id}/showUser")
    public String showUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
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
