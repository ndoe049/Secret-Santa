package com.nathanieldoe.santa.controller;

import com.nathanieldoe.santa.api.model.ExclusionRequest;
import com.nathanieldoe.santa.api.PersonApiImpl;
import com.nathanieldoe.santa.api.exception.InvalidExclusionException;
import com.nathanieldoe.santa.model.Person;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@Tag(name = "Person API")
@RequestMapping("/person")
@PreAuthorize("isAuthenticated()")
@SecurityRequirement(name = "Bearer-Token")
@OpenAPIDefinition(info = @Info(title = "Person API", description = "Person Information"))
public class PersonApiController {

    PersonApiImpl api;

    public PersonApiController(PersonApiImpl api) {
        this.api = api;
    }


    /**
     * @return All of the {@link Person}
     */
    @Operation(summary = "Find all people")
    @GetMapping(path = "list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Person>> list() {
        return ResponseEntity.of(Optional.ofNullable(api.list()));
    }


    /**
     * @param id The ID of the {@link Person} to find
     *
     * @return The {@link Person} object if found
     */
    @Operation(summary = "Find person by ID")
    @GetMapping(path = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> findById(@PathVariable Long id) {
        return ResponseEntity.of(Optional.ofNullable(api.fetchById(id)));
    }

    /**
     * @param personId The ID of the {@link  Person} to add an exclusion to
     * @param request The exclusion to add
     * @return The {@link Person} that was updated
     */
    @Operation(summary = "Adds a person to the list of exclusions for a person")
    @PutMapping(path = "{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> addExclusion(@PathVariable("id") Long personId, @RequestBody ExclusionRequest request) {
        if (request == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No person available to be excluded");
        }

        Optional<Person> response;

        try {
            response = Optional.ofNullable(api.addExclusion(personId, request));
        } catch (InvalidExclusionException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }

        return ResponseEntity.of(response);
    }

    /**
     * @param person The {@link Person} object to create or update
     *
     * @return The {@link Person} that was created or updated
     */
    @Operation(summary = "Create / update person")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> createOrUpdate(@RequestBody Person person) {
        if (person == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No person available to be persisted");
        }

        return ResponseEntity.of(Optional.ofNullable(api.createOrUpdate(person)));
    }


    /**
     * @param person The {@link Person} object to delete
     */
    @Operation(summary = "Delete person")
    @DeleteMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@RequestBody Person person) {
        if (person == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No person available to delete");
        }

        api.delete(person);
        return ResponseEntity.ok().build();
    }

}
