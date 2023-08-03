package com.nathanieldoe.santa.api;

import com.nathanieldoe.santa.api.exception.InvalidExclusionException;
import com.nathanieldoe.santa.api.model.ExclusionRequest;
import com.nathanieldoe.santa.model.Person;
import jakarta.validation.Valid;

import java.util.List;

public interface PersonApi {
    List<Person> list();

    Person fetchById(Long id);

    Person addExclusion(Long personId, @Valid ExclusionRequest request) throws InvalidExclusionException;

    Person createOrUpdate(@Valid Person person);

    void delete(Long personId);

}
