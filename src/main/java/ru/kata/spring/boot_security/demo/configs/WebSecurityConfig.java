package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

//Настройка секьюрности по определенным URL, а также настройка UserDetails и GrantedAuthority.
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final SuccessUserHandler successUserHandler;
    @Autowired
    private UserServiceImpl userService;

    public WebSecurityConfig(SuccessUserHandler successUserHandler) {
        this.successUserHandler = successUserHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
    /*
    permitAll мы говорим предоставить разрешения для следующих url.
    Эти ссылки не будут требовать регистрации
    hasRole предоставляет доступ пользователям с ролью
    Проверка идёт сверху вниз, применяется первое подходящее правило
    hasAnyRole страницам даёт доступ ко всем остальным
     */
        http
                .authorizeRequests()
                .antMatchers("/lesson", "/lesson/newUser").permitAll()
                .antMatchers("/lesson/admin/**").hasRole("ADMIN")
//                .antMatchers("/resources/**", "/static/**", "/css/**","styles.css").permitAll()
                .anyRequest().hasAnyRole("USER", "ADMIN")
                .and()
                .formLogin().successHandler(successUserHandler)
                .permitAll()
                .and()
                //логаут по вызову первой ссылки, вторая это перенаправление после логаута
                .logout().logoutUrl("/logout").logoutSuccessUrl("/lesson");

    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /*
    Проверяет по логину и паролю существует ли такой пользователь или нет
    для аутентификации имени пользователя и пароля.
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setPasswordEncoder(passwordEncoder());
        authProvider.setUserDetailsService(userService);
        return authProvider;
    }
}