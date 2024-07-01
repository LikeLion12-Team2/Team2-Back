package org.smlions.comfortplant.repository;

import org.smlions.comfortplant.domain.entity.Plant;
import org.smlions.comfortplant.domain.entity.Watering;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WateringRepository extends JpaRepository<Watering, Long> {
}
