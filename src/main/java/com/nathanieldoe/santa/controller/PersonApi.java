package com.nathanieldoe.santa.controller;

import com.nathanieldoe.santa.model.Person;

import java.util.List;

public interface PersonApi {
    List<Person> list();

    Person createOrUpdate(Person person);

    void delete(Person person);

}
