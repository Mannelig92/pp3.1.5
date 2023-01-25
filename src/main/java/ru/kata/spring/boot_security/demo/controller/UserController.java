package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

//Помечаем что это класс контроллера для работы с thymeleaf
@Controller
//@RequestMapping помечает общий URL-адрес для каждого метода, например /lesson/allUsers
@RequestMapping("/lesson")
public class UserController {
    private UserService userService;

    //Подключаем Service через конструктор. Лучше через него, а не через поле или сэттэр
    @Autowired
    public UserController(UserService userService) {
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

    /*
    Mapping связывают методы контроллера с тем адресом к которым мы можем обратиться из браузера
    Всего 5 видов Mapping в зависимость от того какой http-запрос должен прийти в этот метод контроллера
    Model это логика работы с данными.
     */
    @GetMapping(value = "/allUsers") //Отображение страницы url
    public String showAllUsers(Model model) { //вывод всех юзеров
        //кладём в model пару ключ,значение. В Thymeleaf используем значение по ключу
        model.addAttribute("allUsers", userService.getAllUsers()); //allUsers имеет отношение в Thymeleaf
        return "allUsers"; //отношение к html файлу по такому же названию
    }

    @GetMapping(value = "/newUser")
    public String saveNewUser(@ModelAttribute("user") User user) { //Добавление нового юзера
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

    //получение юзера по id. вместо id можно будет поместить число и с помощью аннотации PathVariable
    //мы извлёчём этот id из url и получим к нему доступ
    //PathVariable предоставит этот id
    @GetMapping(value = "/{id}/edit")
    public String editUser(@PathVariable("id") long id, Model model) {
        //Получаем текущего человека по его id
        model.addAttribute("user", userService.getUser(id));
        return "edit";
    }

    @PatchMapping(value = "/{id}")
    public String update(@ModelAttribute("user") User user) {
        userService.editUser(user);
        return "redirect:/lesson/allUsers";
    }

    @DeleteMapping(value = "/{id}/delete")
    public String delete(@PathVariable("id") long id) {
        userService.removeUserById(id);
        return "redirect:/lesson/allUsers";
    }
}
