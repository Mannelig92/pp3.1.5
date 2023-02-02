package ru.kata.spring.boot_security.demo.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
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
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String userName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "age")
    private int age;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER) //Понять всё и дописать
    @JoinTable(name = "Users_Roles",
            joinColumns = @JoinColumn(name = "user_id"), //id в таблице юзерс
            inverseJoinColumns = @JoinColumn(name = "role_id")) //id в таблице roles
    private List<Role> roles;
//    @ManyToMany(fetch = FetchType.EAGER)
//    private Collection<Role> roles;

    public User() {
    }

    public User(String userName, String lastName, int age, String password, String email) {
        this.userName = userName;
        this.lastName = lastName;
        this.age = age;
        this.password = password;
        this.email = email;
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