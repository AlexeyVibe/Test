package ru.avmadeit.CarRental.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.avmadeit.CarRental.models.Car;

import java.util.List;


@Repository
public interface CarsRepository extends JpaRepository<Car, Integer> {

    List<Car> findByModelStartingWith (String query);
}
