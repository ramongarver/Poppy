package com.ramongarver.poppy.api.repository;

import com.ramongarver.poppy.api.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {

    List<Activity> findByIdIn(List<Long> ids);

}
