package com.brunoalves.calendarapi.service;

import com.brunoalves.calendarapi.entity.AvailabilitySlot;
import com.brunoalves.calendarapi.repository.AvailabilitySlotRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AvailabilitySlotsService {

    @Autowired
    private AvailabilitySlotRepository availabilitySlotRepository;

    //@Transient
    public List<AvailabilitySlot> validateSlots(List<AvailabilitySlot> availabilitySlotsList) {
        boolean add;
        List<AvailabilitySlot> slotsValidates = new ArrayList<>();

        for (AvailabilitySlot availabilitySlot : availabilitySlotsList) {

            add = ChronoUnit.MINUTES.between(availabilitySlot.getStartTime(), availabilitySlot.getFinishTime()) == 60
                    && StringUtils.endsWithIgnoreCase(availabilitySlot.getStartTime().toString(), "00")
                    && StringUtils.endsWithIgnoreCase(availabilitySlot.getFinishTime().toString(), "00");

            if (add) {
                Optional<AvailabilitySlot> existingSlot = availabilitySlotRepository.findByDayOfWeekAndStartTimeAndFinishTime(availabilitySlot.getDayOfWeek(), availabilitySlot.getStartTime(), availabilitySlot.getFinishTime());
                if (existingSlot.isPresent()) {
                    slotsValidates.add(existingSlot.get());
                } else
                    slotsValidates.add(availabilitySlot);
            }
        }
        return slotsValidates;
    }



}
