package org.smlions.comfortplant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smlions.comfortplant.dto.CreatePlantRequestDTO;
import org.smlions.comfortplant.dto.CreateWateringRequestDTO;
import org.smlions.comfortplant.dto.PlantResponseDTO;
import org.smlions.comfortplant.service.PlantService;
import org.smlions.comfortplant.service.WateringService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/watering")
@Tag(name = "물주기 API", description = "물주기 관련 API입니다.")
public class WateringController {

    private final WateringService wateringService;
    private final PlantService plantService;

    @Operation(method = "POST", summary = "물주기", description = "물주기를 합니다. header에 accessToken과 body에 plantId를 담아서 전송합니다.")
    @PostMapping("/create")
    public ResponseEntity<?> createWatering(@AuthenticationPrincipal UserDetails userDetails,  @RequestBody CreateWateringRequestDTO createWateringRequestDTO){
        wateringService.createWatering(userDetails.getUsername(), createWateringRequestDTO);
        PlantResponseDTO plantResponseDTO = plantService.getPlant(createWateringRequestDTO.getPlantId());

        return new ResponseEntity<>(plantResponseDTO, HttpStatus.CREATED);

    }
}
