package av.TestQuest1.services;

import av.TestQuest1.models.Authority;
import av.TestQuest1.models.Person;
import av.TestQuest1.models.Picture;
import av.TestQuest1.repositories.AuthoritiesRepository;
import av.TestQuest1.repositories.PeopleRepository;

import av.TestQuest1.util.AuthorityNotAddException;
import av.TestQuest1.util.AuthorityValidator;
import av.TestQuest1.util.PersonNotAddException;
import av.TestQuest1.util.PictureValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private PeopleRepository peopleRepository;
    private PicturesService picturesService;
    private AuthoritiesRepository authoritiesRepository;
    private final AuthorityValidator authorityValidator;
    private final PictureValidator pictureValidator;



    @Autowired
    public PeopleService(PeopleRepository peopleRepository, PicturesService picturesService, AuthoritiesRepository authoritiesRepository, AuthorityValidator authorityValidator, PictureValidator pictureValidator) {
        this.peopleRepository = peopleRepository;
        this.picturesService = picturesService;

        this.authoritiesRepository = authoritiesRepository;
        this.authorityValidator = authorityValidator;
        this.pictureValidator = pictureValidator;
    }

    public List<Person> findAll(){
        return peopleRepository.findAll();
    }


    @Transactional
    public int save(Person person){
        enrichPerson(person);
        person.setStatus(person.getStatus().substring(0,1).toUpperCase()+person.getStatus().substring(1));

        if (person.getRoles() != null){

            rolesScroll(person);
        }

        if (person.getPicture() != null) {
            pictureScroll(person);
        }

        peopleRepository.save(person);
        return person.getId();
    }

    public Person findById(int id) {
       Optional<Person> person = peopleRepository.findById(id);
       return person.orElse(null);
    }

    @Transactional
    public  String update(int id , Person person){
        Optional<Person> person1 = peopleRepository.findById(id);
        String oldStatus = person1.get().getStatus();
        person.setId(id);
        enrichPerson(person);
        person.setStatus(person.getStatus().substring(0,1).toUpperCase()+person.getStatus().substring(1));

        if (!person.getRoles().isEmpty()){
            rolesScroll(person);
        }

        if (!person.getPicture().isEmpty()) {
            pictureScroll(person);
        }
        peopleRepository.save(person);
        return "Status before " + oldStatus + " Status after " + person.getStatus();
    }

    public Person findByName(String name){
        return peopleRepository.findByName(name).orElse(null);
    }

    public Person findByEmail(String email){
        return peopleRepository.findByEmail(email).orElse(null);
    }

    private void enrichPerson(Person person){

        person.setChangedAt(new Date());
    }

    private void rolesScroll(Person person){

        List<Authority> roles = new ArrayList<>();

        for (int i = 0; i < person.getRoles().size(); i++){

           Authority authority = authoritiesRepository
                   .findByRole(person.getRoles().get(i).getRole()).orElse(null);

            if (authority == null){
                throw new PersonNotAddException("You've posted wrong role, check the role");
            }

            roles.add(authority);
        }
        person.setRoles(roles);
    }

    private void pictureScroll(Person person){

        List<Picture> pictures = new ArrayList<>();

        for (int i = 0; i < person.getPicture().size(); i++){

            Picture picture = picturesService
                    .findByUrl(person.getPicture().get(i).getUrl()).orElse(null);

            if (picture == null){
                throw new PersonNotAddException("You've posted wrong picture, check the url");
            }

            pictures.add(picture);
        }
        person.setPicture(pictures);
    }
}
