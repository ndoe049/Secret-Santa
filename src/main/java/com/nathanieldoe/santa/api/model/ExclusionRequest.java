package com.nathanieldoe.santa.api.model;

import com.nathanieldoe.santa.model.Person;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;

public record ExclusionRequest(@Valid Person exclusion, @Min(1900) int year) {

    @Override
    public String toString() {
        return "ExclusionRequest{" +
                "exclusion=" + exclusion +
                ", year=" + year +
                '}';
    }

}
