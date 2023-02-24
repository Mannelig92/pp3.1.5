package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.exception_handling.NoSuchUserException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@RestController //тот же контроллер + @ResponseBody над каждым методом
@RequestMapping("/api")
public class RESTController {

    private UserService userService;

    @Autowired
    public RESTController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public ResponseEntity<List<User>> allUsers() {
        List<User> listUsers = userService.getAllUsers();
        return new ResponseEntity<>(listUsers, HttpStatus.OK);
    }

    @GetMapping("/admin/{id}")
    public User getUser(@PathVariable long id) { //@PathVariable для получения значения переменной из адреса запроса
        User user = userService.getUser(id);
        if (user == null) {
            throw new NoSuchUserException("There is no user with ID: " + id + " in Database");
        }
        return user;
    }

    @PostMapping("/admin")
    public User addNewUser(@RequestBody User user) { //@Rb связывает тело HTTP-метода с параметром метода контроллера
        userService.saveUser(user);
        return user;
    }

    @PutMapping("/admin")
    public User editUser(@RequestBody User user) {
        userService.editUser(user);
        return user;
    }

    @DeleteMapping("/admin/{id}")
    public String deleteUser(@PathVariable long id) {
        User user = userService.getUser(id);
        if (user == null) {
            throw new NoSuchUserException("There is no user with ID: " + id + " in Database");
        }
        userService.removeUserById(id);
        return "User with ID: " + id + " was deleted.";
    }
}
