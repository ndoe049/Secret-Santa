package com.nathanieldoe.santa.api;

import com.nathanieldoe.santa.db.PersonRepository;
import com.nathanieldoe.santa.model.Exclusion;
import com.nathanieldoe.santa.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PersonApiImpl implements PersonApi {

    private static final Logger LOG = LoggerFactory.getLogger(PersonApiImpl.class);

    private final PersonRepository personRepository;

    public PersonApiImpl(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @Override
    public List<Person> list() {
        LOG.info("Finding all people");
        return personRepository.findAll();
    }

    @Override
    public Person fetchById(Long id) {
        LOG.info("Finding person by ID: {}", id);
        return personRepository.findById(id).orElse(null);
    }

    @Override
    public Person addExclusion(Long personId, Long exclusionId) {
        if (Objects.equals(personId, exclusionId)) {
            LOG.warn("A person is not allowed to add themselves as an exclusion");
            return null;
        }

        Optional<Person> lookupResult = personRepository.findById(personId);
        if (lookupResult.isPresent()) {
            Person p = lookupResult.get();
            Optional<Person> exclusionPersonResult = personRepository.findById(exclusionId);
            if (exclusionPersonResult.isPresent()) {
                LOG.info("Adding exclusion {} to {}", exclusionId, personId);

                p.getExclusions().add(new Exclusion(exclusionPersonResult.get(), LocalDate.now().getYear()));
                personRepository.save(p);

                return p;
            } else {
                LOG.warn("Unable to find exclusion person with ID {}", exclusionId);
            }
        } else {
            LOG.warn("Unable to find person with ID {}", personId);
        }

        return null;
    }

    @Override
    public Person createOrUpdate(Person person) {
        if (person == null) {
            LOG.warn("No person to persist!");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No person available to be persisted");
        }

        return personRepository.save(person);
    }

    @Override
    public void delete(Person person) {
        if (person == null) {
            LOG.warn("No person to persist!");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No person available to delete");
        }

        personRepository.delete(person);
    }

}
