package com.nathanieldoe.santa;

import com.nathanieldoe.santa.db.PersonRepository;
import com.nathanieldoe.santa.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class PersonIntegrationTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PersonRepository personRepository;


    /**
     * Add people to the test db context
     */
    @BeforeEach
    void setup() {
        testEntityManager.persist(
                new Person("Tony",
                "Stark",
                "ironman@avengers.com")
        );
        testEntityManager.persist(
                new Person("Bruce",
                        "Banner",
                        "hulk@avengers.com")
        );

        testEntityManager.flush();
    }

    @Test
    void findAll() {
        List<Person> all = personRepository.findAll();
        assertThat(all).isNotNull().isNotEmpty().hasSize(2);
    }

    @Test
    void findById() {
        Person scott = new Person("Scott", "Lang", "antman@avengers.com");
        Person persisted = testEntityManager.persist(scott);
        testEntityManager.flush();

        Optional<Person> result = personRepository.findById(persisted.getId());
        assertThat(result).isNotNull().isPresent().hasValue(persisted);
    }

    @Test
    void delete() {
        Optional<Person> tonyResult = personRepository.findFirstByEmailAddressOrderByLastNameAsc("ironman@avengers.com");
        assertThat(tonyResult).isNotNull().isPresent();

        Person tony = tonyResult.get();
        personRepository.delete(tony);
    }

    @Test
    void addExclusion() {
        //TODO
    }

    @Test
    void deletePersonWithExclusion() {
        //TODO
    }

}
