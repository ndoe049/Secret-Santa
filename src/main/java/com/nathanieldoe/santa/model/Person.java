package com.nathanieldoe.santa.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * The representation of a person (i.e. santa)
 */
@Entity
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    Long id;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "email_address")
    String emailAddress;

    /**
     * People that should not be allowed to be picked when generating Santa combinations
     */
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    List<Exclusion> exclusions = new ArrayList<>();

    public Person() {
    }

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

    public List<Exclusion> getExclusions() {
        return exclusions;
    }

    public void setExclusions(List<Exclusion> exclusions) {
        this.exclusions = exclusions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (!getFirstName().equals(person.getFirstName())) return false;
        if (!getLastName().equals(person.getLastName())) return false;
        if (!getEmailAddress().equals(person.getEmailAddress())) return false;
        return getExclusions() != null ? getExclusions().equals(person.getExclusions()) : person.getExclusions() == null;
    }

    @Override
    public int hashCode() {
        int result = getFirstName().hashCode();
        result = 31 * result + getLastName().hashCode();
        result = 31 * result + getEmailAddress().hashCode();
        result = 31 * result + (getExclusions() != null ? getExclusions().hashCode() : 0);
        return result;
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
