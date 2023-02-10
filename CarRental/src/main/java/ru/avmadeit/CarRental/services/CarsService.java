package ru.avmadeit.CarRental.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.avmadeit.CarRental.models.Car;
import ru.avmadeit.CarRental.models.Person;
import ru.avmadeit.CarRental.repositories.CarsRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class CarsService {

    private CarsRepository carsRepository;

    @Autowired
    public CarsService(CarsRepository carsRepository) {
        this.carsRepository = carsRepository;
    }

    public List<Car> findAll() {
        return carsRepository.findAll();
    }

    public Car findOne(int id) {

        Optional<Car> car = carsRepository.findById(id);

        return car.orElse(null);
    }

    public List<Car> findByModel (String query){

        return carsRepository.findByModelStartingWith(query);
    }

    @Transactional
    public void save(Car car) {

        carsRepository.save(car);

    }

    @Transactional
    public void update(int id, Car car){

        car.setId(id);
        carsRepository.save(car);
    }

    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(int id){

        carsRepository.deleteById(id);
    }

    @Transactional
    public void assign(int id, Person selectedPerson){

        carsRepository.findById(id).ifPresent(
                car -> {
                        car.setPerson(selectedPerson);
                        car.setTakenAt(new Date());
            }
        );

    }

    @Transactional
    public void release(int id){

        carsRepository.findById(id).ifPresent(
                car -> {
                    car.setPerson(null);
                    car.setTakenAt(null);
                }
        );
    }

    @Transactional
    public Person getCarOwner(int id){

       return carsRepository.findById(id).map(Car::getPerson).orElse(null);

    }
}
