package com.nathanieldoe.santa;

import com.nathanieldoe.santa.db.ExclusionRepository;
import com.nathanieldoe.santa.db.PersonRepository;
import com.nathanieldoe.santa.model.Exclusion;
import com.nathanieldoe.santa.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
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
    @Autowired
    private ExclusionRepository exclusionRepository;


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

        tonyResult = personRepository.findFirstByEmailAddressOrderByLastNameAsc("ironman@avengers.com");
        assertThat(tonyResult).isNotPresent();
    }

    @Test
    void addExclusion() {
        List<Person> all = personRepository.findAll();
        assertThat(all).isNotNull().isNotEmpty().hasSizeGreaterThanOrEqualTo(2);

        Person first = all.get(0);
        Person second = all.get(1);

        first.addExclusion(new Exclusion(second, LocalDate.now().getYear()));

        testEntityManager.persist(first);
        testEntityManager.flush();

        all = personRepository.findAll();
        List<Exclusion> exclusions = exclusionRepository.findAll();
        assertThat(exclusions).isNotNull().isNotEmpty().hasSize(1);
    }

    @Test
    void deletePersonWithExclusion() {
        addExclusion();

        Person first = personRepository.findAll().get(0);
        testEntityManager.remove(first);
        testEntityManager.flush();

        List<Exclusion> exclusions = exclusionRepository.findAll();
        assertThat(exclusions).isNotNull().isEmpty();

        List<Person> people = personRepository.findAll();
        assertThat(people).isNotNull().hasSize(1);
    }

    @Test
    void addCyclicalExclusions() {
        List<Person> all = personRepository.findAll();
        assertThat(all).isNotNull().isNotEmpty().hasSizeGreaterThanOrEqualTo(2);

        Person first = all.get(0);
        Person second = all.get(1);

        first.addExclusion(new Exclusion(second, LocalDate.now().getYear()));
        second.addExclusion(new Exclusion(first, LocalDate.now().getYear()));

        testEntityManager.persist(first);
        testEntityManager.persist(second);
        testEntityManager.flush();

        List<Exclusion> exclusions = exclusionRepository.findAll();
        assertThat(exclusions).isNotNull().isNotEmpty().hasSize(2);
    }

    @Test
    void deletePersonWithCyclicalExclusion() {
        addCyclicalExclusions();

        List<Person> all = personRepository.findAll();
        List<Exclusion> exclusions = exclusionRepository.findAll();

        Person first = personRepository.findAll().get(0);

        for (Exclusion e : first.getExclusions()) {
            testEntityManager.remove(e);
        }

        for (Exclusion e : first.getExcludedBy()) {
            testEntityManager.remove(e);
        }

        testEntityManager.remove(first);
        testEntityManager.flush();

        exclusions = exclusionRepository.findAll();
        assertThat(exclusions).isNotNull().isEmpty();

        List<Person> people = personRepository.findAll();
        assertThat(people).isNotNull().hasSize(1);


    }

}
