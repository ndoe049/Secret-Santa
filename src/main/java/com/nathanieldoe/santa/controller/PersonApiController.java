package com.nathanieldoe.santa.controller;

import com.nathanieldoe.santa.api.PersonApi;
import com.nathanieldoe.santa.api.PersonApiImpl;
import com.nathanieldoe.santa.db.PersonRepository;
import com.nathanieldoe.santa.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
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
public class PersonApiController {

    PersonApiImpl api;

    public PersonApiController(PersonApiImpl api) {
        this.api = api;
    }

    @GetMapping(path = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> list() {
        return ResponseEntity.of(Optional.ofNullable(api.list()));
    }

    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> findById(@PathVariable Long id) {
        return ResponseEntity.of(Optional.ofNullable(api.fetchById(id)));
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> createOrUpdate(@RequestBody Person person) {
        if (person == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No person available to be persisted");
        }

        return ResponseEntity.of(Optional.ofNullable(api.createOrUpdate(person)));
    }

    @DeleteMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@RequestBody Person person) {
        if (person == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No person available to delete");
        }

        api.delete(person);
        return ResponseEntity.ok().build();
    }

}
