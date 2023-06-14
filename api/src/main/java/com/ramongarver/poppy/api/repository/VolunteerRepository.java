package com.ramongarver.poppy.api.repository;

import com.ramongarver.poppy.api.entity.Volunteer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    Optional<Volunteer> findByEmail(String email);

    List<Volunteer> findByIdIn(List<Long> ids);

}
