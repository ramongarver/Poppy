package com.ramongarver.poppy.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
public class Volunteer extends User {

    private LocalDate startDate;

    private LocalDate endDate;

    @ManyToMany(mappedBy = "volunteers")
    private List<WorkGroup> workGroups = new ArrayList<>();

    @ManyToMany(mappedBy = "volunteers")
    private List<Activity> activities = new ArrayList<>();

}
