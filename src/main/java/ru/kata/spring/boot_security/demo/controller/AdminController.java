package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/lesson/admin")
public class AdminController {
    private UserService userService;
    private RoleServiceImpl roleServiceImpl;

    //Подключаем Service через конструктор. Лучше через него, а не через поле или сэттэр
    @Autowired
    public AdminController(UserService userService, RoleServiceImpl roleServiceImpl) {
        this.userService = userService;
        this.roleServiceImpl = roleServiceImpl;
    }
    /*
    Mapping связывают методы контроллера с тем адресом к которым мы можем обратиться из браузера
    Всего 5 видов Mapping в зависимость от того какой http-запрос должен прийти в этот метод контроллера
    Model это логика работы с данными.
     */
    @GetMapping() //Отображение страницы url
    public String showAllUsers(Model model) { //вывод всех юзеров
        //кладём в model пару ключ,значение. В Thymeleaf используем значение по ключу
        model.addAttribute("admin", userService.getAllUsers()); //allUsers имеет отношение в Thymeleaf
        return "admin"; //отношение к html файлу по такому же названию
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
