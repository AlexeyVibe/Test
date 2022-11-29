package av.TestQuest1.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @OneToMany
    @JoinTable(
            name = "person_authority",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id")
    )
    private List<Authority> roles;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 2,max = 100,message = "Name should contain from 2 to 100 characters")
    @Column(name = "name")
    private String name;

    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 2,max = 100,message = "Name should contain from 2 to 100 characters")
    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private String status;

    @Column(name = "status_change_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date changedAt;

    @OneToMany(mappedBy = "person")
    private List<Picture> picture;


    public List<Authority> getRoles() {
        return roles;
    }

    public void setRoles(List<Authority> roles) {
        this.roles = roles;
    }

    public List<Picture> getPicture() {
        return picture;
    }

    public void setPicture(List<Picture> picture) {
        this.picture = picture;
    }

    public Date getChangedAt() {
        return changedAt;
    }

    public void setChangedAt(Date changedAt) {
        this.changedAt = changedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
