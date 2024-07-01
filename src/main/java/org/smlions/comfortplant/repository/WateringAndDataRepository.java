package org.smlions.comfortplant.repository;

import org.smlions.comfortplant.domain.entity.User;
import org.smlions.comfortplant.domain.entity.Watering;
import org.smlions.comfortplant.domain.entity.WateringAndDateData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WateringAndDataRepository extends JpaRepository<WateringAndDateData, Long> {
    Optional<WateringAndDateData> findByPlantId(Long PlantId);

    List<WateringAndDateData> findAllByPlantId(Long plantId);
}
