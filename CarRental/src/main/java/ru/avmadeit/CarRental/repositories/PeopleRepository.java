package ru.avmadeit.CarRental.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.avmadeit.CarRental.models.Person;

import java.util.List;


@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {

    List<Person> findByFullNameStartingWith (String query);
}
