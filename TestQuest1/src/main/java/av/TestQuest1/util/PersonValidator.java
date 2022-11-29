package av.TestQuest1.util;

import av.TestQuest1.models.Person;
import av.TestQuest1.services.PeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {

    private PeopleService peopleService;

    @Autowired
    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Person.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Person person = (Person) target;

        if (peopleService.findByName(person.getName()) != null
                || peopleService.findByEmail(person.getEmail()) !=null){
            errors.rejectValue("name", "", "This person is registered already");
        }
    }
}
