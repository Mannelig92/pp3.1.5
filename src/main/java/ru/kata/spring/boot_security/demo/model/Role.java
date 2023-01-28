package ru.kata.spring.boot_security.demo.model;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "roles")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @ManyToMany(mappedBy = "roles") //ссылаемся на коллекцию в юзерс
    private List<User> users;
    public Role() {
    }
    public Role(long id) {
        this.id = id;
    }
    public Role(long id, String name) {
        this.id = id;
        this.name = name;
    }
    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @Override
    public String getAuthority() {
        return getName();
    }
    @Override
    public String toString() {
        return "Role{" +
                "users=" + users +
                ", id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}