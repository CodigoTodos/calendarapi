package com.brunoalves.calendarapi.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Person {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="person_id")
    private Long id;
    @NotNull
    private String name;
    private PersonType personType;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "person_availability",
            joinColumns = {@JoinColumn(name = "person_id")},
            inverseJoinColumns = {@JoinColumn(name = "personAvailabilitySlot_id")})
    private List<AvailabilitySlot> availabilitySlotsList;

    public Person(String name,PersonType personType , List<AvailabilitySlot> availabilitySlotsList) {
        this.name = name;
        this.personType = personType;
        this.availabilitySlotsList = availabilitySlotsList;
    }


}
