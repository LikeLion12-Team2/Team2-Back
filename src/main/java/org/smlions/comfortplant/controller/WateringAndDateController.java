package org.smlions.comfortplant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smlions.comfortplant.domain.entity.User;
import org.smlions.comfortplant.dto.WateringAndDateResponseDTO;
import org.smlions.comfortplant.service.WateringAndDateService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wateringanddate")
@Tag(name = "식물보관함 API", description = "식물보관함 관련 API입니다.")
public class WateringAndDateController {

    private final WateringAndDateService wateringAndDateService;

    @Operation(method = "GET", summary = "식물 보관함 조회", description = "식물 보관함 조회를 합니다. header에 accessToken과 url parameter에 plantId를 담아서 전송합니다.")
    @GetMapping("/{plantId}")
    public ResponseEntity<List<WateringAndDateResponseDTO>> getWateringData(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long plantId) {
        List<WateringAndDateResponseDTO> wateringData = wateringAndDateService.getWateringAndDate(plantId);
        return ResponseEntity.ok(wateringData);
    }
}
