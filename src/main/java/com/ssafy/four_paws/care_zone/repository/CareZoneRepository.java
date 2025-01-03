package com.ssafy.four_paws.care_zone.repository;

import com.ssafy.four_paws.care_zone.entity.CareZone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CareZoneRepository extends JpaRepository<CareZone, Long> {

}
