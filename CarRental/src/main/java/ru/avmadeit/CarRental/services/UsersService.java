package ru.avmadeit.CarRental.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.avmadeit.CarRental.models.Car;
import ru.avmadeit.CarRental.models.Person;
import ru.avmadeit.CarRental.models.User;
import ru.avmadeit.CarRental.repositories.UsersRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UsersService {

    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> findAll() {
        return usersRepository.findAll();
    }

    @Transactional
    public void assignAdmin(int id){

        Optional<User> user = usersRepository.findById(id);

        user.get().setRole("ROLE_ADMIN");
        user.get().setId(id);
        usersRepository.save(user.get());
    }

    @Transactional
    public void assignUser(int id){

        Optional<User> user = usersRepository.findById(id);

        user.get().setRole("ROLE_USER");
        user.get().setId(id);
        usersRepository.save(user.get());
    }

    @Transactional
    public void save(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        usersRepository.save(user);
    }

    public Optional<User> findUserByLogin (String login){

        return usersRepository.findByLogin(login);

    }
}
