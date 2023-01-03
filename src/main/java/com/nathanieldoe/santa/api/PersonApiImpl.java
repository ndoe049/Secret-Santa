package com.nathanieldoe.santa.api;

import com.nathanieldoe.santa.db.PersonRepository;
import com.nathanieldoe.santa.model.Person;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PersonApiImpl implements PersonApi {

    private PersonRepository repository;

    public PersonApiImpl(PersonRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Person> list() {
        return repository.findAll();
    }

    @Override
    public Person createOrUpdate(Person person) {
        if (person == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No person available to be persisted");
        }

        return repository.save(person);
    }

    @Override
    public void delete(Person person) {
        if (person == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No person available to delete");
        }

        repository.delete(person);
    }

}
