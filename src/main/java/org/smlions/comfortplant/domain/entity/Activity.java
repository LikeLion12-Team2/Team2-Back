package org.smlions.comfortplant.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.smlions.comfortplant.dto.UpdateActivityReqDto;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "activity")
public class Activity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activityId")
    private long activityId;

    @Column(name = "content")
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "activityStatus", length = 50)
    private ActivityStatus activityStatus;

    @Column(name = "isDone")
    private boolean isDone;

    // 유저 맵핑 부분 추가
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    public void setUser(User user){
        this.user = user;
    }
    public void update(UpdateActivityReqDto updateActivityReqDto){
        content = updateActivityReqDto.getContent();
    }

    public void check(){
        isDone = true;
    }
}
