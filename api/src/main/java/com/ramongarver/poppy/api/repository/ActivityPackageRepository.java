package com.ramongarver.poppy.api.repository;

import com.ramongarver.poppy.api.entity.ActivityPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityPackageRepository extends JpaRepository<ActivityPackage, Long> {

    List<ActivityPackage> findByIdIn(List<Long> ids);

}
