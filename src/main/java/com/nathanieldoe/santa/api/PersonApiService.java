package com.nathanieldoe.santa.api;

import com.nathanieldoe.santa.api.exception.InvalidExclusionException;
import com.nathanieldoe.santa.api.model.ExclusionRequest;
import com.nathanieldoe.santa.db.PersonRepository;
import com.nathanieldoe.santa.model.Exclusion;
import com.nathanieldoe.santa.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PersonApiService implements PersonApi {

    private static final Logger LOG = LoggerFactory.getLogger(PersonApiService.class);

    private final PersonRepository personRepository;

    public PersonApiService(PersonRepository personRepository) {
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
    public Person addExclusion(Long personId, ExclusionRequest request) throws InvalidExclusionException {
        if (request == null) {
            throw new InvalidExclusionException("Exclusion provided was not valid");
        } else if (Objects.equals(personId, request.exclusion().getId())) {
            throw new InvalidExclusionException("A person is not allowed to add themselves as an exclusion");
        }

        Optional<Person> lookupResult = personRepository.findById(personId);
        if (lookupResult.isPresent()) {
            Person p = lookupResult.get();
            Optional<Person> exclusionPersonResult = personRepository.findById(request.exclusion().getId());
            if (exclusionPersonResult.isPresent()) {
                int year = request.year() > 0 ? request.year() : LocalDate.now().getYear();
                LOG.info("Adding exclusion {} to {} for {}", exclusionPersonResult.get().getFullName(), p.getFullName(), year);

                p.addExclusion(new Exclusion(exclusionPersonResult.get(), year));
                personRepository.save(p);

                return p;
            } else {
                LOG.warn("Unable to find exclusion person with ID {}", request.exclusion().getId());
                throw new InvalidExclusionException(
                        MessageFormat.format("Unable to find exclusion person with ID {0}",
                                request.exclusion().getId()));
            }
        } else {
            LOG.warn("Unable to find person with ID {}", personId);
        }

        throw new InvalidExclusionException(
                MessageFormat.format("Unable to find person with ID {0}", personId));
    }

    @Override
    public Person createOrUpdate(Person person) {
        if (person == null) {
            LOG.warn("No person to persist!");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No person available to be persisted");
        }

        LOG.info("Creating / Updating : {}", person.getFullName());
        return personRepository.save(person);
    }

    @Override
    public void delete(Long personId) {
        LOG.info("Deleting : {}", personId);
        Optional<Person> p = personRepository.findById(personId);
        if (p.isPresent()) {
            personRepository.delete(p.get());
        } else {
            personRepository.deleteById(personId);
        }
    }

}
