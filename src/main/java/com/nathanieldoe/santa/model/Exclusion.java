package com.nathanieldoe.santa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nathanieldoe.santa.util.PersonExclusionSerializer;
import jakarta.persistence.*;

import java.util.Objects;

/**
 * The representation of an exclusion to a person for a given year.
 * Each person may have multiple exclusions associated with themselves.
 */
@Entity
@Table(name = "exclusions")
public class Exclusion {
    @Id
    @JsonIgnore
    @GeneratedValue
    @Column(nullable = false)
    Long id;

    @ManyToOne
    @JsonIgnore
    Person sender;

    @ManyToOne
    @JsonSerialize(using = PersonExclusionSerializer.class)
    Person receiver;

    @Column(name = "exclude_year", nullable = false)
    int year;

    public Exclusion() {
    }

    /**
     * @param sender Person that is the sender
     * @param receiver Person to exclude from being picked as a receiver
     * @param year The secret santa year the exclusion should apply to
     */
    public Exclusion(Person sender, Person receiver, int year) {
        this.sender = sender;
        this.receiver = receiver;
        this.year = year;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getReceiver() {
        return receiver;
    }

    public void setReceiver(Person receiver) {
        this.receiver = receiver;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Exclusion exclusion = (Exclusion) o;

        if (!getReceiver().equals(exclusion.getReceiver())) return false;
        return Objects.equals(getYear(), exclusion.getYear());
    }

    @Override
    public int hashCode() {
        int result = getReceiver().hashCode();
        result = 31 * result + getYear();
        return result;
    }

    @Override
    public String toString() {
        return "Exclusion{" +
                "id=" + id +
                ", sender=" + sender.id +
                ", receiver=" + receiver +
                ", year=" + year +
                '}';
    }
}
