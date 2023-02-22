package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    boolean saveUser(User user); //Добавление юзера

    void removeUserById(long id); //Удаление юзеров

    List<User> getAllUsers(); //Вывод всех юзеров

    void editUser(User user);

    User getUser(long id);

    Set<Role> getRole();

    Optional<User> findByUserName(String username);
}
