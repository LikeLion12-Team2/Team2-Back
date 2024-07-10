package org.smlions.comfortplant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smlions.comfortplant.dto.CreatePlantRequestDTO;
import org.smlions.comfortplant.dto.CreateUserRequestDto;
import org.smlions.comfortplant.dto.PlantResponseDTO;
import org.smlions.comfortplant.dto.UserResponseDTO;
import org.smlions.comfortplant.service.PlantService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/plant")
@Tag(name = "식물 API", description = "식물 관련 API입니다.")
public class PlantController {
    private final PlantService plantService;

    @Operation(method = "POST", summary = "식물 생성", description = "식물 생성을 합니다. header에 accessToken과 body에는 식물의 Type(TREE, FLOWER)과 식물의 이름을 담아서 전송합니다.")
    @PostMapping("/create")
    public ResponseEntity<?> createPlant(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CreatePlantRequestDTO createPlantRequestDTO){
        PlantResponseDTO plantResponseDTO = plantService.createPlant(createPlantRequestDTO);
        return new ResponseEntity<>(plantResponseDTO, HttpStatus.CREATED);

    }

    @Operation(method = "GET", summary = "현재 키우는 식물 조회", description = "현재 키우는 식물을 조회합니다. header에 accessToken과 url parameter에 식물Id를 담아서 전송합니다.")
    @GetMapping("/{plantId}")
    public ResponseEntity<?> getPlant(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long plantId){
        return ResponseEntity.ok(plantService.getPlant(plantId));
    }
}
