package com.nathanieldoe.santa.controller;

import com.nathanieldoe.santa.db.PersonRepository;
import com.nathanieldoe.santa.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonApiController implements PersonApi {

    @Autowired
    PersonRepository repository;

    @Override
    @GetMapping("list")
    public List<Person> list() {
        return repository.findAll();
    }

    @Override
    @PostMapping
    public Person createOrUpdate(@RequestBody Person person) {
        if (person == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No person available to be persisted");
        }

        return repository.save(person);
    }

    @Override
    @DeleteMapping
    public void delete(@RequestBody Person person) {
        if (person == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No person available to delete");
        }

        repository.delete(person);
    }

}
