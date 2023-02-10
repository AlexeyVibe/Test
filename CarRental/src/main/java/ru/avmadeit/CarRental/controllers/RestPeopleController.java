package ru.avmadeit.CarRental.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.avmadeit.CarRental.dto.PersonDTO;
import ru.avmadeit.CarRental.models.Person;
import ru.avmadeit.CarRental.services.PeopleService;
import ru.avmadeit.CarRental.utils.ErrorCollector;
import ru.avmadeit.CarRental.utils.PersonErrorResponse;
import ru.avmadeit.CarRental.utils.PersonNotAddException;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restpeople")
public class RestPeopleController {

    private PeopleService peopleService;

    @Autowired
    public RestPeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping
    public List<PersonDTO> findAll(){
        return peopleService.findAll().stream().map(PersonDTO::new).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid PersonDTO personDTO,
                                             BindingResult bindingResult){

        Person person = new Person(personDTO);

        String error = new ErrorCollector().ReadBindingResult(bindingResult);

        if (error != null){
            throw new PersonNotAddException(error);
        }

        peopleService.save(person);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid PersonDTO personDTO,
                                             BindingResult bindingResult,
                                             @PathVariable("id") int id) {

        Person person = new Person(personDTO);

        String error = new ErrorCollector().ReadBindingResult(bindingResult);

        if (error != null) {
            throw new PersonNotAddException(error);
        }

        peopleService.update(id, person);

        return ResponseEntity.ok(HttpStatus.OK);
    }


    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotAddException e){

        PersonErrorResponse response = new PersonErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
