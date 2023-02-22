package ru.kata.spring.boot_security.demo.exception_handling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice //представляет функциональность Global Exception Handler,ловятся исключения во всех Rest контроллерах
public class UserGlobalExceptionHandler {
    @ExceptionHandler //отмечается метод ответственный за обработку исключений
    public ResponseEntity<UserIncorrectData> handleException(NoSuchUserException exception){
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler //Проверка на некорректный ввод
    public ResponseEntity<UserIncorrectData> handleException(Exception exception){
        UserIncorrectData data = new UserIncorrectData();
        data.setInfo(exception.getMessage());
        return new ResponseEntity<>(data, HttpStatus.BAD_REQUEST);
    }
}
