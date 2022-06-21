package com.brunoalves.calendarapi.repository;

import com.brunoalves.calendarapi.entity.AvailabilitySlot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface AvailabilitySlotRepository extends JpaRepository<AvailabilitySlot, Long> {
    Optional<AvailabilitySlot> findByDayOfWeekAndStartTimeAndFinishTime(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime finishTime);
}
