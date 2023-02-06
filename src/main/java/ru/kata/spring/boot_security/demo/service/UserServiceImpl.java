package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/*
этот класс представляет собой сервис – компонент сервис-слоя.
Сервис является подтипом класса @Component.
Использование данной аннотации позволит искать бины-сервисы автоматически.
 */
@Service //Сервис является соединительным звеном между Контроллером и Дао
public class UserServiceImpl implements UserService, UserDetailsService { //Класс сервиса для работы с вэбом

    private UserRepository userRepository;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public boolean saveUser(User user) {
        Optional<User> userDB = userRepository.findByUserName(user.getUserName());
        if (userDB.isPresent()) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.saveAndFlush(user);
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
        userRepository.save(user);
    }

    @Override
    public User getUser(long id) { //Получение юзера по айди
        return userRepository.findById(id).get();
    }

    public Optional<User> findByUserName(String username) { //получение юзера по имени
        return userRepository.findByUserName(username);
    }

    @Override
    @Transactional
    //Загружает пользователя по имени пользователя
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = findByUserName(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
//        return new org.springframework.security.core.userdetails.User(user.get().getUserName(),
//                user.get().getPassword(), mapRolesToAuthorities(user.get().getRoles()));
        return user.get();
    }

    //Метод из коллекции ролей получает коллекцию прав доступа
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().
                map(x -> new SimpleGrantedAuthority(x.getRole())).collect(Collectors.toList());
    }
}