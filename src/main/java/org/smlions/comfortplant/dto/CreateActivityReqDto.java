package org.smlions.comfortplant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.smlions.comfortplant.domain.entity.Activity;
import org.smlions.comfortplant.domain.entity.ActivityStatus;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateActivityReqDto {

    private String content;

    // 유저, 식물 관련 정보까지 필요하면 추가
    public Activity toEntity(){
        return Activity.builder()
                .content(content)
                .activityStatus(ActivityStatus.ADD)
                .isDone(false)                      // 기본값으로 설정
                .build();
    }


}
