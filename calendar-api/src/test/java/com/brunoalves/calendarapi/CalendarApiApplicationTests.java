package com.brunoalves.calendarapi;

import com.brunoalves.calendarapi.configuration.TestsConfiguration;
import com.brunoalves.calendarapi.entity.AvailabilitySlot;
import com.brunoalves.calendarapi.entity.Person;
import com.brunoalves.calendarapi.entity.PersonType;
import com.brunoalves.calendarapi.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CalendarApiApplicationTests {

	@Test
	void contextLoads() {
	}

}
