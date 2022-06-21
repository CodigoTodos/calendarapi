package com.brunoalves.calendarapi.service;

import com.brunoalves.calendarapi.entity.AvailabilitySlot;
import com.brunoalves.calendarapi.entity.Person;
import com.brunoalves.calendarapi.entity.PersonType;
import com.brunoalves.calendarapi.exceptions.APICalendarException;
import com.brunoalves.calendarapi.repository.AvailabilitySlotRepository;
import com.brunoalves.calendarapi.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.util.*;
import java.util.stream.Collectors;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private AvailabilitySlotRepository availabilitySlotRepository;

    @Autowired
    private AvailabilitySlotsService availabilitySlotsService;

    public List<Person> findAll() {
        return personRepository.findAll();
    }

    public Person findById(final Long id) {
        return personRepository.findById(id)
                .orElseThrow(() -> new APICalendarException(APICalendarException.PERSON_NOT_FOUND + id));
    }

    public Person create(Person person) {
        if (!StringUtils.hasText(person.getName())) {
            throw new APICalendarException(APICalendarException.PERSON_WITH_BLANK_NAME);
        }
        List<AvailabilitySlot> validatedSlots = availabilitySlotsService.validateSlots(person.getAvailabilitySlotsList());
        person.setAvailabilitySlotsList(validatedSlots);
        return personRepository.save(person);
    }

    public void delete(final long id) {
        final Person person = personRepository.findById(id)
                .orElseThrow(() -> new APICalendarException(APICalendarException.PERSON_NOT_FOUND + id));
        personRepository.delete(person);
    }

    public Person update(long id, Person person) {
        Person personToSave = personRepository.findById(id).orElseThrow(() -> new APICalendarException(APICalendarException.PERSON_NOT_FOUND + id));
        personToSave.setPersonType(person.getPersonType());
        personToSave.setName(person.getName());
        personToSave.setAvailabilitySlotsList(person.getAvailabilitySlotsList());
        return personRepository.save(personToSave);
    }

    public List<AvailabilitySlot> getAvailability(List<Long> personIdList) {
        Optional<Person> candidate  = personRepository.findById(personIdList.get(0));
        List<AvailabilitySlot> interviewerAvailability;
        List<AvailabilitySlot> availabilitySlotsInCommon;

        if (candidate.isPresent() && candidate.get().getPersonType().equals(PersonType.CANDIDATE)) {
            availabilitySlotsInCommon = candidate.get().getAvailabilitySlotsList();
        }
        else
           throw  new APICalendarException(APICalendarException.CANDIDATE_NOT_FOUND + personIdList.get(0));

        for (int i = 1; i < personIdList.size(); i++) {
            Optional<Person> interviewer = personRepository.findById(personIdList.get(i));
            if (interviewer.isPresent() && interviewer.get().getPersonType().equals(PersonType.INTERVIEWER)) {
                interviewerAvailability = interviewer.get().getAvailabilitySlotsList();
                availabilitySlotsInCommon = availabilitySlotsInCommon.stream()
                        .filter(interviewerAvailability::contains).collect(Collectors.toCollection(ArrayList::new));
            }
            else
                throw  new APICalendarException(APICalendarException.INTERVIEWER_NOT_FOUND + personIdList.get(i));

        }
         return availabilitySlotsInCommon;
    }



}
