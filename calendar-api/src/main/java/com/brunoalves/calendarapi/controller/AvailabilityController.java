package com.brunoalves.calendarapi.controller;

import com.brunoalves.calendarapi.entity.AvailabilitySlot;
import com.brunoalves.calendarapi.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AvailabilityController {

    @Autowired
    private PersonService personService;

    @GetMapping(value="/availability/{personsIdList}")
    public List<AvailabilitySlot> getAvailability(@PathVariable List<Long> personsIdList) {

        return personService.getAvailability(personsIdList);
    }

}


