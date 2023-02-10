package ru.avmadeit.CarRental.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.avmadeit.CarRental.dto.CarDTO;
import ru.avmadeit.CarRental.dto.PersonDTO;
import ru.avmadeit.CarRental.models.Car;
import ru.avmadeit.CarRental.models.Person;
import ru.avmadeit.CarRental.services.CarsService;
import ru.avmadeit.CarRental.utils.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restcars")
public class RestCarController {

    private CarsService carsService;

    @Autowired
    public RestCarController(CarsService carsService) {
        this.carsService = carsService;
    }

    @GetMapping
    public List<CarDTO> findAll(){
        return carsService.findAll().stream().map(CarDTO::new).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid CarDTO carDTO,
                                             BindingResult bindingResult){

        Car car = new Car(carDTO);

        String error = new ErrorCollector().ReadBindingResult(bindingResult);

        if (error != null){
            throw new PersonNotAddException(error);
        }

        carsService.save(car);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid CarDTO carDTO,
                                             BindingResult bindingResult,
                                             @PathVariable("id") int id) {

        Car car = new Car(carDTO);

        String error = new ErrorCollector().ReadBindingResult(bindingResult);

        if (error != null) {
            throw new PersonNotAddException(error);
        }

        carsService.update(id, car);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<CarErrorResponse> handleException(CarNotAddException e){

        CarErrorResponse response = new CarErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


}
