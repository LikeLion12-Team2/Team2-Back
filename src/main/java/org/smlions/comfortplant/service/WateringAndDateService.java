package org.smlions.comfortplant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smlions.comfortplant.domain.entity.Plant;
import org.smlions.comfortplant.domain.entity.WateringAndDateData;
import org.smlions.comfortplant.dto.PlantResponseDTO;
import org.smlions.comfortplant.dto.WateringAndDateResponseDTO;
import org.smlions.comfortplant.repository.PlantRepository;
import org.smlions.comfortplant.repository.WateringAndDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WateringAndDateService {
    private final PlantRepository plantRepository;
    private final WateringAndDataRepository wateringAndDataRepository;
    @Transactional
    public List<WateringAndDateResponseDTO> getWateringAndDate(Long plantId){
        Plant plant = plantRepository.findById(plantId).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 식물입니다."));

        List<WateringAndDateData> wateringAndDateDataList = wateringAndDataRepository.findAllByPlantId(plant.getId());

        return wateringAndDateDataList.stream()
                .map(WateringAndDateResponseDTO::from)
                .collect(Collectors.toList());

    }
}
