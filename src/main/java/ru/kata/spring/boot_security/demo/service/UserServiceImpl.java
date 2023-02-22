package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/*
этот класс представляет собой сервис – компонент сервис-слоя.
Сервис является подтипом класса @Component.
Использование данной аннотации позволит искать бины-сервисы автоматически.
 */
@Service //Сервис является соединительным звеном между Контроллером и Дао
public class UserServiceImpl implements UserService { //Класс сервиса для работы с вэбом

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public boolean saveUser(User user) {
        Optional<User> userDB = userRepository.findByUserName(user.getUserName());
        if (userDB.isPresent()) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    @Transactional
    public void removeUserById(long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void editUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User getUser(long id) { //Получение юзера по айди
        return userRepository.findById(id).get();
    }

    @Override
    public Optional<User> findByUserName(String username) { //получение юзера по имени
        return userRepository.findByUserName(username);
    }

    @Override
    public Set<Role> getRole() {
        return new HashSet<>(roleRepository.findAll());
    }
}