package av.TestQuest1.services;

import av.TestQuest1.models.Authority;
import av.TestQuest1.models.Person;
import av.TestQuest1.repositories.AuthoritiesRepository;
import av.TestQuest1.repositories.PeopleRepository;
import av.TestQuest1.util.AuthorityNotAddException;
import av.TestQuest1.util.PersonNotAddException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class AuthoritiesService {

    private AuthoritiesRepository authoritiesRepository;
    private PeopleRepository peopleRepository;

    @Autowired
    public AuthoritiesService(AuthoritiesRepository authoritiesRepository
            , PeopleRepository peopleRepository) {
        this.authoritiesRepository = authoritiesRepository;
        this.peopleRepository = peopleRepository;

    }

    public List<Authority> findAll(){
        return authoritiesRepository.findAll();
    }

    @Transactional
    public void save(Authority authority){

        if (!authority.getPeople().isEmpty()){
            peopleScroll(authority);
        }

        authoritiesRepository.save(authority);
    }

    @Transactional
    public Authority update(int id, Authority authority){
        authority.setId(id);
        if (!authority.getPeople().isEmpty()){
            peopleScroll(authority);
        }
        return authoritiesRepository.save(authority);
    }

    public Authority findByRole(String role){
        return authoritiesRepository.findByRole(role).orElse(null);
    }

    private void peopleScroll(Authority authority){
        List<Person> people = new ArrayList<>();

        for (int i = 0; i < authority.getPeople().size(); i++){
            Person person = peopleRepository.findByName(authority.getPeople().get(i).getName()).orElse(null);
                if (person == null){
                    throw new AuthorityNotAddException("You've posted a wrong person, check the name or email");
                }
                people.add(person);
        }

        authority.setPeople(people);
    }




}
