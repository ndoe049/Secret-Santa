package com.nathanieldoe.santa.api.model;

import com.nathanieldoe.santa.model.Person;

public record ExclusionRequest(Person exclusion, Integer year) {

    @Override
    public String toString() {
        return "ExclusionRequest{" +
                "exclusion=" + exclusion +
                ", year=" + year +
                '}';
    }

}
