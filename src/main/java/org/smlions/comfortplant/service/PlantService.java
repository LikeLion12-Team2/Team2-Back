package org.smlions.comfortplant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smlions.comfortplant.domain.entity.Plant;
import org.smlions.comfortplant.domain.entity.Status;
import org.smlions.comfortplant.domain.entity.User;
import org.smlions.comfortplant.domain.entity.WateringAndDateData;
import org.smlions.comfortplant.dto.CreatePlantRequestDTO;
import org.smlions.comfortplant.dto.CreateUserRequestDto;
import org.smlions.comfortplant.dto.PlantResponseDTO;
import org.smlions.comfortplant.dto.UserResponseDTO;
import org.smlions.comfortplant.repository.PlantRepository;
import org.smlions.comfortplant.repository.WateringAndDataRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlantService {
    private final PlantRepository plantRepository;
    private final WateringAndDataRepository wateringAndDataRepository;
    @Transactional
    public PlantResponseDTO createPlant(CreatePlantRequestDTO createPlantRequestDTO){

        Plant plant = createPlantRequestDTO.toEntity();

        plant.setFirstTime();

        plantRepository.save(plant);

        return PlantResponseDTO.from(plant);

    }

    @Transactional
    public PlantResponseDTO getPlant(Long plantId){
        Plant plant = plantRepository.findById(plantId).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 식물입니다."));
        return PlantResponseDTO.from(plant);

    }

    // 매일 23시 59분 59초에 각 plantId 별로 날짜와 totalWateringCount 저장
    @Scheduled(cron = "59 59 23 * * *")
    @Transactional
    public void saveTotalWateringCountAndDate() {
        List<Plant> plants = plantRepository.findAll();

        for (Plant plant : plants) {
            WateringAndDateData wateringAndDateData = new WateringAndDateData();
            wateringAndDateData.setDataWatering(LocalDate.now(), plant.getTotalWateringCount(), plant);
            wateringAndDataRepository.save(wateringAndDateData);

        }
    }

    // 매일 00시 00분 00초에 각 plantId별로 totalWateringCount 0으로 초기화
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void resetTotalWateringCount() {
        List<Plant> plants = plantRepository.findAll();

        for (Plant plant : plants) {
            plant.setTotalWateringCount(Long.valueOf(0));
            plantRepository.save(plant);
        }
    }


}
