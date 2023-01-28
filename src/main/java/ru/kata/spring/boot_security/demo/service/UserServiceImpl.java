package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/*
этот класс представляет собой сервис – компонент сервис-слоя.
Сервис является подтипом класса @Component.
Использование данной аннотации позволит искать бины-сервисы автоматически.
 */
@Service //Сервис является соединительным звеном между Контроллером и Дао
public class UserServiceImpl implements UserService, UserDetailsService { //Класс сервиса для работы с вэбом
    @Autowired
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //    private PasswordEncoder passwordEncoder;
//    @Autowired
//    public UserServiceImpl(@Lazy PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }
    @Override
    @Transactional
    public boolean saveUser(User user) {
        Optional<User> userDB = userRepository.findByUserName(user.getUserName());
        if (userDB.isEmpty()) {
            return false;
        }
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
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

    //    @Override
//    @Transactional
//    public void editUser(User user) {
//        Optional<User> userEdit = userRepository.findByUserName(user.getUserName());
//        userEdit.set
//    }
    @Override
    public User getUser(long id) {
        Optional<User> userFromDB = userRepository.findById(id);
        return userFromDB.orElse(new User());
    }

    @Override
    @Transactional
    //Загружает пользователя по имени пользователя
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUserName(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return user.get(); //Не уверен
    }
}