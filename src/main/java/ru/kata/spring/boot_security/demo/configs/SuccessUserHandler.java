package ru.kata.spring.boot_security.demo.configs;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

/*
1.Настроить смену пароля +
2.Настроить сокрытие ссылок для ролей +
3.Настроить ввод данных для полей
 */
@Component
public class SuccessUserHandler implements AuthenticationSuccessHandler {
    // Действие после успешной аутентификации
    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException {
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        if (roles.contains("ROLE_ADMIN")) {
            httpServletResponse.sendRedirect("/lesson/admin");
        } else if (roles.contains("ROLE_USER")) {
            httpServletResponse.sendRedirect("/lesson/showUser");
        } else {
            httpServletResponse.sendRedirect("/lesson");
        }
    }
}