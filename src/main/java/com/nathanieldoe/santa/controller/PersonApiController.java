package com.nathanieldoe.santa.controller;

import com.nathanieldoe.santa.api.PersonApiImpl;
import com.nathanieldoe.santa.model.Person;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/person")
@PreAuthorize("isAuthenticated()")
@SecurityRequirement(name = "Secret Santa API")
@OpenAPIDefinition(info = @Info(title = "Person API", description = "Person Information"))
public class PersonApiController {

    PersonApiImpl api;

    public PersonApiController(PersonApiImpl api) {
        this.api = api;
    }


    /**
     * @return All of the {@link Person}
     */
    @GetMapping(path = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> list() {
        return ResponseEntity.of(Optional.ofNullable(api.list()));
    }


    /**
     * @param id The ID of the {@link Person} to find
     *
     * @return The {@link Person} object if found
     */
    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> findById(@PathVariable Long id) {
        return ResponseEntity.of(Optional.ofNullable(api.fetchById(id)));
    }

    /**
     * @param person The {@link Person} object to create or update
     *
     * @return The {@link Person} that was created or updated
     */
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> createOrUpdate(@RequestBody Person person) {
        if (person == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No person available to be persisted");
        }

        return ResponseEntity.of(Optional.ofNullable(api.createOrUpdate(person)));
    }


    /**
     * @param person The {@link Person} object to delete
     */
    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@RequestBody Person person) {
        if (person == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No person available to delete");
        }

        api.delete(person);
        return ResponseEntity.ok().build();
    }

}
