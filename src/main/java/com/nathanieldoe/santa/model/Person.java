package com.nathanieldoe.santa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.text.MessageFormat;
import java.util.*;

/**
 * The representation of a person (i.e. santa)
 */
@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column
    @GeneratedValue
    Long id;

    @NotNull
    @Column(name = "first_name")
    String firstName;

    @NotNull
    @Column(name = "last_name")
    String lastName;

    @NotNull
    @Column(name = "email_address")
    String emailAddress;

    /**
     * People that should not be allowed to be picked when generating Santa combinations
     */
    @ManyToMany(cascade = { CascadeType.ALL })
    @Fetch(FetchMode.JOIN)
    @JoinTable(
            name = "person_exclusion",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "exclusion_id")
    )
    Set<Exclusion> exclusions = new HashSet<>();

    /*
     * JPA no-arg
     */
    public Person() {}

    /**
     * @param firstName First name
     * @param lastName Last name
     * @param emailAddress Email address to receive alerts
     */
    public Person(String firstName, String lastName, String emailAddress) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }

    public void addExclusion(Exclusion exclusion) {
        if (Objects.nonNull(exclusion)) {
            this.exclusions.add(exclusion);
        }
    }

    @JsonIgnore
    public String getFullName() {
        return MessageFormat.format("{0} {1}", getFirstName(), getLastName());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Set<Exclusion> getExclusions() {
        return exclusions;
    }

    public void setExclusions(Set<Exclusion> exclusions) {
        this.exclusions = exclusions;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", exclusions='" + exclusions.size() + '\'' +
                '}';
    }

}
