package com.ramongarver.poppy.api.repository;

import com.ramongarver.poppy.api.entity.WorkGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkGroupRepository extends JpaRepository<WorkGroup, Long> {

    List<WorkGroup> findByIdIn(List<Long> ids);

}
