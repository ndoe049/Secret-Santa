package com.nathanieldoe.santa.api;

import com.nathanieldoe.santa.model.Person;

import java.util.List;

public interface PersonApi {
    List<Person> list();

    Person fetchById(Long id);

    Person createOrUpdate(Person person);

    void delete(Person person);

}
