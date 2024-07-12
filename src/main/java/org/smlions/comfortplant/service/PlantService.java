package org.smlions.comfortplant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smlions.comfortplant.domain.entity.*;
import org.smlions.comfortplant.dto.*;
import org.smlions.comfortplant.repository.PlantRepository;
import org.smlions.comfortplant.repository.UserRepository;
import org.smlions.comfortplant.repository.WateringAndDataRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlantService {
    private final PlantRepository plantRepository;
    private final WateringAndDataRepository wateringAndDataRepository;
    private final UserRepository userRepository;
    @Transactional
    public PlantResponseDTO createPlant(String email, CreatePlantRequestDTO createPlantRequestDTO){

        User user = userRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));

        Plant plant = createPlantRequestDTO.toEntity();

        plant.setFirstTime();

        plant.setUser(user);
        plantRepository.save(plant);

        return PlantResponseDTO.from(plant);

    }



    public List<PlantResponseDTO> getCompletePlants(Long userId) {
        List<Plant> plants = plantRepository.findAllByUserId(userId);
        List<PlantResponseDTO> plantResponseDTOList = new ArrayList<>();

        for (Plant plant : plants) {
            if (plant.getStatus().equals(Status.Complete)) {
                plantResponseDTOList.add(PlantResponseDTO.from(plant));
            }
        }
        return plantResponseDTOList;
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
