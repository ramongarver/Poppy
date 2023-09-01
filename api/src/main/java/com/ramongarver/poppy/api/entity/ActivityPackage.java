package com.ramongarver.poppy.api.entity;

import com.ramongarver.poppy.api.enums.ActivityPackageType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class ActivityPackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private ActivityPackageType type;

    @Column(nullable = false)
    private LocalDate availabilityStartDate;

    @Column(nullable = false)
    private LocalDate availabilityEndDate;

    @Column(nullable = false, columnDefinition = "int default 2")
    private Integer maxActivitiesPerVolunteer = 2;

    @Column(nullable = false, columnDefinition = "int default 3")
    private Integer minCoordinatorsToIgnoreLimit = 3;

    @Column(nullable = false, columnDefinition = "boolean default true")
    private Boolean isVisible = true;

    @OneToMany(mappedBy = "activityPackage")
    private List<Activity> activities = new ArrayList<>();

    @OneToMany(mappedBy = "activityPackage")
    private List<VolunteerAvailability> volunteerAvailabilities = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean areVolunteersAssigned = false;

}
