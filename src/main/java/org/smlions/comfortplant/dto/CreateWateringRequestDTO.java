package org.smlions.comfortplant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.smlions.comfortplant.domain.entity.Plant;
import org.smlions.comfortplant.domain.entity.User;
import org.smlions.comfortplant.domain.entity.Watering;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateWateringRequestDTO {
    public Long plantId;

    public Watering toEntity() {
        return Watering.builder()
                .build();
    }
}
