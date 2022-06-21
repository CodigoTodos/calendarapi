package com.brunoalves.calendarapi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class AvailabilitySlot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="personAvailabilitySlot_id")
    private long id;
    private DayOfWeek dayOfWeek;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime startTime;
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime finishTime;

    @ManyToMany(mappedBy = "availabilitySlotsList")
    @JsonBackReference
    private List<Person> persons;

    public AvailabilitySlot(DayOfWeek dayOfWeek, LocalTime startTime, LocalTime finishTime) {
        this.dayOfWeek = dayOfWeek;
        this.startTime = startTime;
        this.finishTime = finishTime;

    }

}
