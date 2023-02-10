package ru.avmadeit.CarRental.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.avmadeit.CarRental.models.User;
import ru.avmadeit.CarRental.services.UsersService;

import java.util.Optional;

@Component
public class UserValidator implements Validator {

    private UsersService usersService;

    @Autowired
    public UserValidator(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        User newUser = (User) target;

        Optional<User> user = usersService.findUserByLogin(newUser.getLogin());

        if (user.isEmpty()) {
            return;
        }

        errors.rejectValue("login", "", "This login is already taken");
    }
}
