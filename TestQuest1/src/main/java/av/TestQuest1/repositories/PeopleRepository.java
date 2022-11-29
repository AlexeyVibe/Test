package av.TestQuest1.repositories;

import av.TestQuest1.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
     Optional<Person> findByName (String name);
     Optional<Person> findByEmail (String email);
 }
