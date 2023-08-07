package com.ramongarver.poppy.api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class VolunteerAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "volunteer_id", nullable = false)
    private Volunteer volunteer;

    @ManyToOne
    @JoinColumn(name = "activity_package_id", nullable = false)
    private ActivityPackage activityPackage;

    @ManyToMany
    @JoinTable(
            name = "volunteer_availability_activity",
            joinColumns = @JoinColumn(name = "volunteer_availability_id"),
            inverseJoinColumns = @JoinColumn(name = "activity_id")
    )
    private List<Activity> activities = new ArrayList<>();

}
