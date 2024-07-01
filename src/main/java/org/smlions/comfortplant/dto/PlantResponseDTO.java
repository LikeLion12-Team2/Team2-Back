package org.smlions.comfortplant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.smlions.comfortplant.domain.entity.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class PlantResponseDTO {

    public Long id;
    public Type type;
    public Status status;
    public Long level;
    public Long totalExp;
    public Long currentExp;
    public String name;

    public String backgroundItem;
    public String colorItem;

    public static PlantResponseDTO from(Plant plant){
        return PlantResponseDTO.builder()
                .id(plant.getId())
                .type(plant.getType())
                .level(plant.getLevel())
                .currentExp(plant.getCurrentExp())
                .totalExp(plant.getTotalExp())
                .status(plant.getStatus())
                .name(plant.getName())
                .backgroundItem(plant.getBackgroundItem())
                .colorItem(plant.getColorItem())
                .build();
    }
}
