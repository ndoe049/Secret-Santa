package com.nathanieldoe.santa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nathanieldoe.santa.util.PersonExclusionSerializer;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

/**
 * The representation of an exclusion to a person for a given year.
 * Each person may have multiple exclusions associated with themselves.
 */
@Entity
@Table(name = "exclusions")
public class Exclusion {
    @Id
    @Column
    @JsonIgnore
    @GeneratedValue
    Long id;

    @ManyToMany(mappedBy = "exclusions")
    @JsonSerialize(using = PersonExclusionSerializer.class)
    Set<Person> receiver = new HashSet<>();

    @Column(name = "exclude_year", nullable = false)
    int year;

    /*
     * JPA no-arg
     */
    public Exclusion() {}

    /**
     * @param receiver Person to exclude from being picked as a receiver
     * @param year The secret santa year the exclusion should apply to
     */
    public Exclusion(Person receiver, int year) {
        this.receiver.add(receiver);
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Person> getReceiver() {
        return receiver;
    }

    public void setReceiver(Set<Person> receiver) {
        this.receiver = receiver;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Exclusion{" +
                "id=" + id +
                ", receiver=" + receiver +
                ", year=" + year +
                '}';
    }
}
