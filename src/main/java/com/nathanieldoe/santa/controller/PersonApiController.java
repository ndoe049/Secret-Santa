package com.nathanieldoe.santa.controller;

import com.nathanieldoe.santa.api.PersonApi;
import com.nathanieldoe.santa.api.PersonApiImpl;
import com.nathanieldoe.santa.db.PersonRepository;
import com.nathanieldoe.santa.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/person")
@PreAuthorize("isAuthenticated()")
public class PersonApiController {

    PersonApiImpl api;

    public PersonApiController(PersonApiImpl api) {
        this.api = api;
    }

    @GetMapping("list")
    public List<Person> list() {
        return api.list();
    }

    @PostMapping
    public Person createOrUpdate(@RequestBody Person person) {
        if (person == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No person available to be persisted");
        }

        return api.createOrUpdate(person);
    }

    @DeleteMapping
    public void delete(@RequestBody Person person) {
        if (person == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No person available to delete");
        }

        api.delete(person);
    }

}
