package org.smlions.comfortplant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smlions.comfortplant.domain.entity.Plant;
import org.smlions.comfortplant.domain.entity.Status;
import org.smlions.comfortplant.domain.entity.User;
import org.smlions.comfortplant.dto.CreatePlantRequestDTO;
import org.smlions.comfortplant.dto.CreateUserRequestDto;
import org.smlions.comfortplant.dto.PlantResponseDTO;
import org.smlions.comfortplant.dto.UserResponseDTO;
import org.smlions.comfortplant.repository.PlantRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlantService {
    private final PlantRepository plantRepository;
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
}
