package av.TestQuest1.controllers;

import av.TestQuest1.dto.PersonDTO;
import av.TestQuest1.dto.PersonResponse;
import av.TestQuest1.models.Person;
import av.TestQuest1.repositories.PeopleRepository;
import av.TestQuest1.services.PeopleService;
import av.TestQuest1.util.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/people")
public class PersonController {

    private final PeopleService peopleService;
    private final ModelMapper modelMapper;
    private final PersonValidator personValidator;
    private final AuthorityValidator authorityValidator;
    private final PictureValidator pictureValidator;
    private PeopleRepository peopleRepository;

    @Autowired
    public PersonController(PeopleService peopleService, ModelMapper modelMapper, PersonValidator personValidator, AuthorityValidator authorityValidator, PictureValidator pictureValidator, PeopleRepository peopleRepository) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
        this.personValidator = personValidator;
        this.authorityValidator = authorityValidator;
        this.pictureValidator = pictureValidator;
        this.peopleRepository = peopleRepository;
    }

    @GetMapping
    public PersonResponse findAll(@RequestParam(required = false) String status,
                                   @RequestParam(required = false) Long timestamp){
        if (status == null || timestamp == null) {
            return new PersonResponse(peopleService.findAll().stream()
                    .map(this::ConvertToPersonDTO).collect(Collectors.toList()));
        }else return new PersonResponse(peopleService.findAll().stream().filter(person -> status.equals(person.getStatus()))
                .filter(person -> new Date(timestamp).before(person.getChangedAt()))
                .map(this::ConvertToPersonDTO).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public PersonDTO findById(@PathVariable("id") int id){
        return ConvertToPersonDTO(peopleService.findById(id));
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody @Valid PersonDTO personDTO,
                                         BindingResult bindingResult){

        personValidator.validate(ConvertToPerson(personDTO),bindingResult);
//        ConvertToPerson(personDTO).getRoles().stream().forEach(authority -> authorityValidator.validate(authority.getRole(),bindingResult));
//        ConvertToPerson(personDTO).getPicture().stream().forEach(picture -> personValidator.validate(picture.getUrl(),bindingResult));

        String error = new ErrorCollector().ReadBindingResult(bindingResult);

        if (error != null){
            throw new PersonNotAddException(error);
        }

        int id = peopleService.save(ConvertToPerson(personDTO));
       return ResponseEntity.ok("id is - " + id) ;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@RequestBody @Valid PersonDTO personDTO,
                                         BindingResult bindingResult,
                                         @PathVariable("id") int id){

        personValidator.validate(ConvertToPerson(personDTO),bindingResult);

        String error = new ErrorCollector().ReadBindingResult(bindingResult);

        if (error != null){
            throw new PersonNotAddException(error);
        }

        return ResponseEntity.ok(peopleService.update(id,ConvertToPerson(personDTO)));
    }

    @ExceptionHandler
    private ResponseEntity<PersonErrorResponse> handleException(PersonNotAddException e){

        PersonErrorResponse response = new PersonErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Person ConvertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO,Person.class);
    }

    private PersonDTO ConvertToPersonDTO(Person person) {
        return modelMapper.map(person,PersonDTO.class);
    }
}
