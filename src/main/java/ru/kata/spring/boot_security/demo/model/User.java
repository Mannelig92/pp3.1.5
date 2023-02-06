package ru.kata.spring.boot_security.demo.model;


import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;


import java.util.Collection;
import java.util.List;

/*
UserDetails можно представить, как адаптер между БД пользователейи тем что требуется Spring Security внутри
SecurityContextHolder.
 */
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty(message = "Строка не должна быть пустой")
    @Column(name = "first_name")
    private String userName;
    @NotEmpty(message = "Строка не должна быть пустой")
    @Column(name = "last_name")
    private String lastName;
    @NotEmpty(message = "Строка не должна быть пустой")
    @Size(min = 16, max = 110, message = "Возраст должен быть в пределах 16-110 лет")
    @Column(name = "age")
    private int age;
    @NotEmpty(message = "Строка не должна быть пустой")
    @Column(name = "password")
    private String password;
    @NotEmpty(message = "Строка не должна быть пустой")
    @Email(message = "Почта должна иметь правильный вид")
    @Column(name = "email")
    private String email;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER) //Понять всё и дописать
    @JoinTable(name = "Users_Roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), //id в таблице юзерс
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")) //id в таблице roles
    private List<Role> roles;

    public User() {
    }

    public User(String userName, String lastName, int age, String password, String email, List<Role> roles) {
        this.userName = userName;
        this.lastName = lastName;
        this.age = age;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() { //аккаунт не просрочен
        return true;
    }

    @Override
    public boolean isAccountNonLocked() { //аккаунт не заблокирован
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() { //пароль не просрочен
        return true;
    }

    @Override
    public boolean isEnabled() { //аккаунт включён, работает
        return true;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + userName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }
}