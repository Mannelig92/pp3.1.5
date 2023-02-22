package ru.kata.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    /*
    Мне подсказали сделать выборку через квэри, стало на 5 селектов меньше,
    Это подходит для решения n+1 или читал ещё про @BatchSize, можно ли использовать его для решения проблемы?
     */
    @Query("select u from User u where u.userName = :name")
    Optional<User> findByUserName(String name);
}