package org.smlions.comfortplant.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smlions.comfortplant.dto.CreatePlantRequestDTO;
import org.smlions.comfortplant.dto.CreateUserRequestDto;
import org.smlions.comfortplant.dto.PlantResponseDTO;
import org.smlions.comfortplant.dto.UserResponseDTO;
import org.smlions.comfortplant.service.PlantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plant")
public class PlantController {
    private final PlantService plantService;

    @PostMapping("/create")
    public ResponseEntity<?> createPlant(@RequestBody CreatePlantRequestDTO createPlantRequestDTO){
        PlantResponseDTO plantResponseDTO = plantService.createPlant(createPlantRequestDTO);
        return new ResponseEntity<>(plantResponseDTO, HttpStatus.CREATED);

    }

    @GetMapping("/{plantId}")
    public ResponseEntity<?> getPlant(@PathVariable Long plantId){
        return ResponseEntity.ok(plantService.getPlant(plantId));
    }
}
