package ru.avmadeit.CarRental.models;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table (name = "Exploiter")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty(message = "Login shouldn't be empty")
    @Size(min = 2, max = 100, message = "Login should contain from 2 to 100 characters")
    @Column(name = "login")
    private String login;

    @NotEmpty(message = "Password shouldn't be empty")
    @Size(min = 2, max = 100, message = "Password should contain from 2 to 100 characters")
    @Column(name = "password")
    private String password;

    @Column(name = "role")
    private String role;


    public User(){

    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
