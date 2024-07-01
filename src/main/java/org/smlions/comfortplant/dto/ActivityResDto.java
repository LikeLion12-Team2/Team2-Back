package org.smlions.comfortplant.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.smlions.comfortplant.domain.entity.Activity;
import org.smlions.comfortplant.domain.entity.ActivityStatus;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivityResDto {

    private Long activityId;
    private ActivityStatus activityStatus;
    private String content;

    // 유저, 식물 관련 정보까지 필요하면 추가
    public ActivityResDto from(Activity activity){
        return ActivityResDto.builder()
                .activityId(activity.getActivityId())
                .content(activity.getContent())
                .activityStatus(activity.getActivityStatus())
                .build();
    }
}
