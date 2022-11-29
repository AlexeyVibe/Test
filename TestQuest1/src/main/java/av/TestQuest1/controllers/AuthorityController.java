package av.TestQuest1.controllers;

import av.TestQuest1.dto.AuthorityDTO;
import av.TestQuest1.dto.AuthorityResponse;
import av.TestQuest1.models.Authority;
import av.TestQuest1.services.AuthoritiesService;
import av.TestQuest1.util.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/authority")
public class AuthorityController {

    private AuthoritiesService authoritiesService;
    private AuthorityValidator authorityValidator;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthorityController(AuthoritiesService authoritiesService, AuthorityValidator authorityValidator, ModelMapper modelMapper) {
        this.authoritiesService = authoritiesService;
        this.authorityValidator = authorityValidator;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public AuthorityResponse findAll(){
        return new AuthorityResponse(authoritiesService.findAll().stream().map(this::convertToAuthorityDTO).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid AuthorityDTO authorityDTO,
                                             BindingResult bindingResult){

        authorityValidator.validate(convertToAuthority(authorityDTO),bindingResult);

        String error = new ErrorCollector().ReadBindingResult(bindingResult);

        if (error != null){
            throw new AuthorityNotAddException(error);
        }
         authoritiesService.save(convertToAuthority(authorityDTO));
        return ResponseEntity.ok(HttpStatus.OK) ;
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> update(@RequestBody @Valid AuthorityDTO authorityDTO,
                                         BindingResult bindingResult,
                                         @PathVariable("id") int id){

        authorityValidator.validate(convertToAuthority(authorityDTO),bindingResult);

        String error = new ErrorCollector().ReadBindingResult(bindingResult);

        if (error != null){
            throw new AuthorityNotAddException(error);
        }

        Authority authority = authoritiesService.update(id,convertToAuthority(authorityDTO));

        return ResponseEntity.ok(authority.getInfo()+authority.getRole()+authority.getPeople());
    }

    @ExceptionHandler
    private ResponseEntity<AuthorityErrorResponse> handleException(AuthorityNotAddException e){

        AuthorityErrorResponse response = new AuthorityErrorResponse(
                e.getMessage(),
                System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private Authority convertToAuthority(AuthorityDTO authorityDTO) {
        return modelMapper.map(authorityDTO,Authority.class);
    }

    private AuthorityDTO convertToAuthorityDTO(Authority authority) {
        return modelMapper.map(authority,AuthorityDTO.class);
    }
}
