package ru.kata.spring.boot_security.demo.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;
import java.util.Optional;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //ищем юзера по его имени пользователя
    //либо может быть такой человек, либо нет
    Optional<User> findByUserName(String name);
}