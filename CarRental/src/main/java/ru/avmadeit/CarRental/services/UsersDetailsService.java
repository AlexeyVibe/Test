package ru.avmadeit.CarRental.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.avmadeit.CarRental.models.User;
import ru.avmadeit.CarRental.repositories.UsersRepository;
import ru.avmadeit.CarRental.security.UserDetails;

import java.util.Optional;

@Service

public class UsersDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Autowired
    public UsersDetailsService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public org.springframework.security.core.userdetails.UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<User> user = usersRepository.findByLogin(username);

       if (user.isEmpty())
           throw new UsernameNotFoundException("User not found");

       return new UserDetails(user.get());
    }
}
