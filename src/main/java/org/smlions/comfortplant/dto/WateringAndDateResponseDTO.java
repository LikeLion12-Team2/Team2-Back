package org.smlions.comfortplant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.smlions.comfortplant.domain.entity.User;
import org.smlions.comfortplant.domain.entity.WateringAndDateData;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class WateringAndDateResponseDTO {
    public LocalDate data;
    public Long wateringCount;

    public static WateringAndDateResponseDTO from(WateringAndDateData wateringAndDateData){
        return WateringAndDateResponseDTO.builder()
                .data(wateringAndDateData.getData())
                .wateringCount(wateringAndDateData.getTotalWatering())
                .build();
    }
}
