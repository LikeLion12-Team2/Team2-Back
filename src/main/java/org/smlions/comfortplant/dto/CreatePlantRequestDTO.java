package org.smlions.comfortplant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.smlions.comfortplant.domain.entity.Plant;
import org.smlions.comfortplant.domain.entity.Type;
import org.smlions.comfortplant.domain.entity.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreatePlantRequestDTO {
    public Type type;
    public String name;

    public Plant toEntity() {
        return Plant.builder()
                .type(type)
                .name(name)
                .build();
    }
}
