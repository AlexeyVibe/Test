package av.TestQuest1.util;

import av.TestQuest1.models.Authority;
import av.TestQuest1.models.Person;
import av.TestQuest1.services.AuthoritiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class AuthorityValidator implements Validator {

    private AuthoritiesService authoritiesService;

    @Autowired
    public AuthorityValidator(AuthoritiesService authoritiesService) {
        this.authoritiesService = authoritiesService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Authority.class);
    }

    @Override
    public void validate(Object target, Errors errors) {

        Authority authority = (Authority) target;

        if (authoritiesService.findByRole(authority.getRole()) != null){
            errors.rejectValue("role", "", "This authority is registered already");
        }
    }
}
