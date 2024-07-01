package org.smlions.comfortplant.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.smlions.comfortplant.domain.entity.Activity;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateActivityReqDto {

    private Long activityId;
    private String content;

    public Activity toEntity(){
        return Activity.builder()
                .content(content)
                .build();
    }
}
