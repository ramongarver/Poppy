package com.ramongarver.poppy.api.repository;

import com.ramongarver.poppy.api.entity.VolunteerAvailability;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VolunteerAvailabilityRepository extends JpaRepository<VolunteerAvailability, Long> {

    List<VolunteerAvailability> findAllByActivityPackageId(Long activityPackageId);

    Optional<VolunteerAvailability> findByActivityPackageIdAndVolunteerId(Long activityPackageId, Long volunteerId);

}
