package av.TestQuest1.dto;


import av.TestQuest1.models.Authority;
import av.TestQuest1.models.Person;
import av.TestQuest1.models.Picture;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class PersonDTO {

    private String name;

    private String email;

    private List<String> picture;

    private List<String> roles;

    public PersonDTO(){}

    public PersonDTO(Person person) {
        this.name = person.getName();
        this.email = person.getEmail();
        this.picture = new ArrayList<>();
        if (person.getPicture() != null){
            for (Picture p: person.getPicture()) {
                this.picture.add(p.getUrl());
            }
        }
        this.roles = new ArrayList<>();
        if (person.getRoles() != null){
            for (Authority a: person.getRoles()) {
                this.roles.add(a.getRole());
            }
        }
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

    public List<String> getPicture() {
        return picture;
    }

    public void setPicture(List<String> picture) {
        this.picture = picture;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
