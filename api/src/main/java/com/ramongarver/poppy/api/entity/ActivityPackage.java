package com.ramongarver.poppy.api.entity;

import com.ramongarver.poppy.api.enums.ActivityPackageType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean isVisible;

    @OneToMany
    @JoinColumn(name = "activity_package_id")
    private List<Activity> activities = new ArrayList<>();

    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean areVolunteersAssigned;

}
