package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class AdminController {
    private UserService userService;

    //Подключаем Service через конструктор. Лучше через него, а не через поле или сэттэр
    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
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
}
