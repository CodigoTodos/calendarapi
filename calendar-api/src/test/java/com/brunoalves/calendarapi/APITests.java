package com.brunoalves.calendarapi;

import com.brunoalves.calendarapi.configuration.TestsConfiguration;
import com.brunoalves.calendarapi.entity.AvailabilitySlot;
import com.brunoalves.calendarapi.entity.Person;
import com.brunoalves.calendarapi.entity.PersonType;
import com.brunoalves.calendarapi.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;

class APITests extends TestsConfiguration {

    public static final String HTTP_LOCALHOST = "http://localhost:";
    public static final String PERSONS = "/persons/";
    public static final String AVAILABILITY = "/availability/";

    @Autowired
    private PersonRepository personRepository;

    @Test
    void testAddPerson() {

        Person response = restTemplate.postForObject(HTTP_LOCALHOST + port + PERSONS, getInterviewerInes(), Person.class);

        assertAll(
                () -> assertEquals(1, response.getId()),
                () -> assertEquals("Ines", response.getName()),
                () -> assertEquals(35, response.getAvailabilitySlotsList().size())
        );
    }

    @Test
    void testFindAllPersons() {

        restTemplate.postForObject(HTTP_LOCALHOST + port + PERSONS, getInterviewerInes(), Person.class);
        restTemplate.postForObject(HTTP_LOCALHOST + port + PERSONS, getInterviewerIngrid(), Person.class);

        assertEquals(2, personRepository.findAll().size());
    }

    @Test
    void testFindPersonById() {
        restTemplate.postForObject(HTTP_LOCALHOST + port + PERSONS, getInterviewerInes(), Person.class);
        Person interviewer = restTemplate.getForObject(HTTP_LOCALHOST + port + PERSONS + "/{id}", Person.class, 1);
        assertAll(
                () -> assertNotNull(interviewer),
                () -> assertEquals(1, interviewer.getId()),
                () -> assertEquals("Ines", interviewer.getName())
        );
    }

    @Test
    void testUpdatePerson() {
        restTemplate.postForObject(HTTP_LOCALHOST + port + PERSONS, getInterviewerInes(), Person.class);
        restTemplate.put(HTTP_LOCALHOST + port + PERSONS + "{id}", getInterviewerInesUpdate(), 1);
        Person person = personRepository.findById(1L).get();

        assertAll(
                () -> assertNotNull(person),
                () -> assertEquals("Ines Maria", person.getName()),
                () -> assertEquals(3, person.getAvailabilitySlotsList().size())
        );
    }

    @Test
    void testDeletePersonById() {
        restTemplate.postForObject(HTTP_LOCALHOST + port + PERSONS, getInterviewerInes(), Person.class);
        assertEquals(1, personRepository.findAll().size());
        restTemplate.delete(HTTP_LOCALHOST + port + PERSONS + "/{id}", 1);
        assertEquals(0, personRepository.findAll().size());

    }

    @Test
    void findPersonByIdNotExistsShouldReturnError() {

        final ResponseEntity<String> responseEntity = restTemplate.getForEntity(HTTP_LOCALHOST + port + PERSONS + "/1", String.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void addPersonWithNoNameShouldReturnError() {

        final ResponseEntity<String> responseEntity = restTemplate.postForEntity(HTTP_LOCALHOST + port + PERSONS, getPersonWithNoName(), String.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void testAddInterviewerWithSlotsErrorsShouldReturnInterviewerJustWithTheValidSlots() {

        Person response = restTemplate.postForObject(HTTP_LOCALHOST + port + PERSONS, getPersonWithSlotsErrors(), Person.class);

        assertAll(
                () -> assertEquals(1, response.getId()),
                () -> assertEquals("Bruno", response.getName()),
                () -> assertEquals(1, response.getAvailabilitySlotsList().size())
        );
    }

    @Test
    void testGetAvailabilityForOneCandidateAndTwoInterviewers() {

        restTemplate.postForObject(HTTP_LOCALHOST + port + PERSONS, getCandidateCarl(), Person.class);
        restTemplate.postForObject(HTTP_LOCALHOST + port + PERSONS, getInterviewerInes(), Person.class);
        restTemplate.postForObject(HTTP_LOCALHOST + port + PERSONS, getInterviewerIngrid(), Person.class);
        AvailabilitySlot[] commonSlots = restTemplate.getForObject(HTTP_LOCALHOST + port + AVAILABILITY + "/{personsIdList}",AvailabilitySlot[].class, "1,2,3");
        assertAll(

                () -> assertEquals(2, commonSlots.length),
                () -> assertEquals(DayOfWeek.TUESDAY.toString(), commonSlots[0].getDayOfWeek().toString()),
                () -> assertEquals("09:00", commonSlots[0].getStartTime().toString()),
                () -> assertEquals("10:00", commonSlots[0].getFinishTime().toString()),
                () -> assertEquals(DayOfWeek.THURSDAY.toString(), commonSlots[1].getDayOfWeek().toString()),
                () -> assertEquals("09:00", commonSlots[1].getStartTime().toString()),
                () -> assertEquals("10:00", commonSlots[1].getFinishTime().toString())
        );
    }

    public Person getInterviewerInes() {
        List<AvailabilitySlot> availabilitySlots = new ArrayList<>();
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.MONDAY, LocalTime.parse("09:00"), LocalTime.parse("10:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.MONDAY, LocalTime.parse("10:00"), LocalTime.parse("11:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.MONDAY, LocalTime.parse("11:00"), LocalTime.parse("12:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.MONDAY, LocalTime.parse("12:00"), LocalTime.parse("13:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.MONDAY, LocalTime.parse("13:00"), LocalTime.parse("14:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.MONDAY, LocalTime.parse("14:00"), LocalTime.parse("15:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.MONDAY, LocalTime.parse("15:00"), LocalTime.parse("16:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.TUESDAY, LocalTime.parse("09:00"), LocalTime.parse("10:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.TUESDAY, LocalTime.parse("10:00"), LocalTime.parse("11:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.TUESDAY, LocalTime.parse("11:00"), LocalTime.parse("12:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.TUESDAY, LocalTime.parse("12:00"), LocalTime.parse("13:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.TUESDAY, LocalTime.parse("13:00"), LocalTime.parse("14:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.TUESDAY, LocalTime.parse("14:00"), LocalTime.parse("15:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.TUESDAY, LocalTime.parse("15:00"), LocalTime.parse("16:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.WEDNESDAY, LocalTime.parse("09:00"), LocalTime.parse("10:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.WEDNESDAY, LocalTime.parse("10:00"), LocalTime.parse("11:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.WEDNESDAY, LocalTime.parse("11:00"), LocalTime.parse("12:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.WEDNESDAY, LocalTime.parse("12:00"), LocalTime.parse("13:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.WEDNESDAY, LocalTime.parse("13:00"), LocalTime.parse("14:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.WEDNESDAY, LocalTime.parse("14:00"), LocalTime.parse("15:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.WEDNESDAY, LocalTime.parse("15:00"), LocalTime.parse("16:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.THURSDAY, LocalTime.parse("09:00"), LocalTime.parse("10:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.THURSDAY, LocalTime.parse("10:00"), LocalTime.parse("11:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.THURSDAY, LocalTime.parse("11:00"), LocalTime.parse("12:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.THURSDAY, LocalTime.parse("12:00"), LocalTime.parse("13:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.THURSDAY, LocalTime.parse("13:00"), LocalTime.parse("14:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.THURSDAY, LocalTime.parse("14:00"), LocalTime.parse("15:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.THURSDAY, LocalTime.parse("15:00"), LocalTime.parse("16:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.FRIDAY, LocalTime.parse("09:00"), LocalTime.parse("10:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.FRIDAY, LocalTime.parse("10:00"), LocalTime.parse("11:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.FRIDAY, LocalTime.parse("11:00"), LocalTime.parse("12:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.FRIDAY, LocalTime.parse("12:00"), LocalTime.parse("13:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.FRIDAY, LocalTime.parse("13:00"), LocalTime.parse("14:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.FRIDAY, LocalTime.parse("14:00"), LocalTime.parse("15:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.FRIDAY, LocalTime.parse("15:00"), LocalTime.parse("16:00")));
        return new Person("Ines", PersonType.INTERVIEWER ,availabilitySlots);
    }

    public Person getInterviewerInesUpdate() {
        List<AvailabilitySlot> availabilitySlots = new ArrayList<>();
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.MONDAY, LocalTime.parse("09:00"), LocalTime.parse("10:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.MONDAY, LocalTime.parse("10:00"), LocalTime.parse("11:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.MONDAY, LocalTime.parse("11:00"), LocalTime.parse("12:00")));
        return new Person("Ines Maria",PersonType.INTERVIEWER,availabilitySlots);
    }

    public Person getInterviewerIngrid() {
        List<AvailabilitySlot> availabilitySlots = new ArrayList<>();
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.MONDAY, LocalTime.parse("12:00"), LocalTime.parse("13:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.MONDAY, LocalTime.parse("13:00"), LocalTime.parse("14:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.MONDAY, LocalTime.parse("14:00"), LocalTime.parse("15:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.MONDAY, LocalTime.parse("15:00"), LocalTime.parse("16:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.MONDAY, LocalTime.parse("17:00"), LocalTime.parse("18:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.TUESDAY, LocalTime.parse("09:00"), LocalTime.parse("10:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.TUESDAY, LocalTime.parse("10:00"), LocalTime.parse("11:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.TUESDAY, LocalTime.parse("11:00"), LocalTime.parse("12:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.WEDNESDAY, LocalTime.parse("12:00"), LocalTime.parse("13:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.WEDNESDAY, LocalTime.parse("13:00"), LocalTime.parse("14:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.WEDNESDAY, LocalTime.parse("14:00"), LocalTime.parse("15:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.WEDNESDAY, LocalTime.parse("15:00"), LocalTime.parse("16:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.WEDNESDAY, LocalTime.parse("17:00"), LocalTime.parse("18:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.THURSDAY, LocalTime.parse("09:00"), LocalTime.parse("10:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.THURSDAY, LocalTime.parse("10:00"), LocalTime.parse("11:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.THURSDAY, LocalTime.parse("11:00"), LocalTime.parse("12:00")));
        return new Person("Ingrid", PersonType.INTERVIEWER, availabilitySlots);
    }

    public Person getCandidateCarl() {
        List<AvailabilitySlot> availabilitySlots = new ArrayList<>();
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.MONDAY, LocalTime.parse("09:00"), LocalTime.parse("10:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.TUESDAY, LocalTime.parse("09:00"), LocalTime.parse("10:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.WEDNESDAY, LocalTime.parse("10:00"), LocalTime.parse("11:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.WEDNESDAY, LocalTime.parse("11:00"), LocalTime.parse("12:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.THURSDAY, LocalTime.parse("09:00"), LocalTime.parse("10:00")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.FRIDAY, LocalTime.parse("10:00"), LocalTime.parse("11:00")));
        return new Person("Carl", PersonType.CANDIDATE, availabilitySlots);
    }

    public Person getPersonWithNoName() {
        List<AvailabilitySlot> availabilitySlots = new ArrayList<>();
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.MONDAY, LocalTime.parse("09:30"), LocalTime.parse("10:30")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.MONDAY, LocalTime.parse("11:00"), LocalTime.parse("12:15")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.MONDAY, LocalTime.parse("11:00"), LocalTime.parse("12:00")));
        return new Person("",PersonType.INTERVIEWER ,availabilitySlots);
    }

    public Person getPersonWithSlotsErrors() {
        List<AvailabilitySlot> availabilitySlots = new ArrayList<>();
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.MONDAY, LocalTime.parse("09:30"), LocalTime.parse("10:30")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.MONDAY, LocalTime.parse("11:00"), LocalTime.parse("12:15")));
        availabilitySlots.add(new AvailabilitySlot(DayOfWeek.MONDAY, LocalTime.parse("11:00"), LocalTime.parse("12:00")));
        return new Person("Bruno",PersonType.CANDIDATE ,availabilitySlots);
    }


}
