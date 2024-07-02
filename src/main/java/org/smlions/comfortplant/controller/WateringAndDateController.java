package org.smlions.comfortplant.controller;

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
public class WateringAndDateController {

    private final WateringAndDateService wateringAndDateService;


    @GetMapping("{plantId}")
    public ResponseEntity<List<WateringAndDateResponseDTO>> getWateringData(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long plantId) {
        List<WateringAndDateResponseDTO> wateringData = wateringAndDateService.getWateringAndDate(plantId);
        return ResponseEntity.ok(wateringData);
    }
}
