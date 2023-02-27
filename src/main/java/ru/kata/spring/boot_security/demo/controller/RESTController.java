package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.exception_handling.NoSuchUserException;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
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
    public List<User> allUsers() {
        List<User> listUsers = userService.getAllUsers();
        return listUsers;
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
    public ResponseEntity<User> addNewUser(@RequestBody User user) { //@Rb связывает тело HTTP-метода с параметром метода контроллера
        userService.saveUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<User> editUser(@RequestBody User user, @PathVariable("id") Long id) {
        user.setId(id);
        userService.editUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
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
    @GetMapping("/user")
    public ResponseEntity<User> showUser(Principal principal) {
        User user = userService.findByUserName(principal.getName()).orElse(null);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
