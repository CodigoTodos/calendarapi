package com.brunoalves.calendarapi.controller;

import com.brunoalves.calendarapi.entity.Person;
import com.brunoalves.calendarapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @GetMapping
    public List<Person> findAll() {
        return personService.findAll();
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable final Long id) {
        return personService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person create(@RequestBody Person person) {
        return personService.create(person);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable final Long id) {
        personService.delete(id);
    }

    @PutMapping("/{id}")
    public Person update(@RequestBody Person person, @PathVariable long id) {
        return personService.update(id, person);
    }



}
