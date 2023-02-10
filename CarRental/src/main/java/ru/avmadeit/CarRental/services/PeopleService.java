package ru.avmadeit.CarRental.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.avmadeit.CarRental.models.Car;
import ru.avmadeit.CarRental.models.Person;
import ru.avmadeit.CarRental.repositories.PeopleRepository;


import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {

    private PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {

        Optional<Person> person = peopleRepository.findById(id);

        return person.orElse(null);
    }

    @Transactional
    public void save(Person person) {
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person person) {
        person.setId(id);
        peopleRepository.save(person);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }

    public List<Car> getCarsByPersonId(int id) {

        Optional<Person> person = peopleRepository.findById(id);

        if (person.isPresent()){
            Hibernate.initialize(person.get().getCars());
            person.get().getCars().forEach(
                    car ->{
                        long calculate = Math.abs(car.getTakenAt().getTime() - new Date().getTime());
                        if (calculate > 604800000)
                            car.setExpired(true);
                    }
                    );
        }

        return person.get().getCars();
    }

    public List<Person> findByFullName (String query){

        List<Person> people =peopleRepository.findByFullNameStartingWith(query);

        people.forEach( person -> person.getCars().forEach(
                car ->{
                    long calculate = Math.abs(car.getTakenAt().getTime() - new Date().getTime());
                    if (calculate > 604800000)
                        car.setExpired(true);
                }
            )
        );

        return people;
    }
}
