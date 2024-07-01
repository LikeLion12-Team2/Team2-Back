package org.smlions.comfortplant.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smlions.comfortplant.dto.CreatePlantRequestDTO;
import org.smlions.comfortplant.dto.CreateWateringRequestDTO;
import org.smlions.comfortplant.dto.PlantResponseDTO;
import org.smlions.comfortplant.service.PlantService;
import org.smlions.comfortplant.service.WateringService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/watering")
public class WateringController {

    private final WateringService wateringService;
    private final PlantService plantService;

    @PostMapping("/create")
    public ResponseEntity<?> createWatering(@RequestBody CreateWateringRequestDTO createWateringRequestDTO){
        wateringService.createWatering(createWateringRequestDTO);
        PlantResponseDTO plantResponseDTO = plantService.getPlant(createWateringRequestDTO.getPlantId());
        log.info("plantResponseDTO 찾았음");
        return new ResponseEntity<>(plantResponseDTO, HttpStatus.CREATED);

    }
}
