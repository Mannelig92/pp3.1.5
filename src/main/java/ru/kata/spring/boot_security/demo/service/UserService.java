package ru.kata.spring.boot_security.demo.service;



import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    void saveUser(User user); //Добавление юзера

    void removeUserById(long id); //Удаление юзеров

    List<User> getAllUsers(); //Вывод всех юзеров

    void editUser(User user);

    User getUser(long id);
}
