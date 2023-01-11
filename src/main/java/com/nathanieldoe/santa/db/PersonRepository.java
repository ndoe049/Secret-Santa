package com.nathanieldoe.santa.db;

import com.nathanieldoe.santa.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findFirstByEmailAddressOrderByLastNameAsc(String emailAddress);

}
