package org.smlions.comfortplant.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "plant")
public class Plant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private Long level;

    @Column
    private Long totalExp;

    @Column
    private Long currentExp;


    @Column
    private Long totalWateringCount;

    @Column
    @Enumerated(EnumType.STRING)
    private Type type;

    @Column
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private LocalDate localDate;

    @OneToMany(mappedBy = "plant", cascade = CascadeType.ALL)
    private List<WateringAndDateData> wateringAndDateData;

    public void setFirstTime(){
        currentExp = Long.valueOf(0);
        level = Long.valueOf(1);
        status = Status.Incomplete;
        totalExp = Long.valueOf(2);
        totalWateringCount = Long.valueOf(0);
    }

    //wateringCount만큼 현재 경험치를 추가 및 레벨업이 가능한지 체크
    public void plusCurrentExp(Long number){
        currentExp+=number;
        FullLevelCheck();
        checkLevelUp();
    }
    //레벨업을 하고, 레벨에 따라 총경험치를 바꿔주는 메서드
    public void LevelUp() {
        level+=1;

        if (level == 2) {
            totalExp = Long.valueOf(4);
        } else if (level == 3) {
            totalExp = Long.valueOf(5);
        } else if (level == 4) {
            totalExp = Long.valueOf(8);
        } else if (level == 5) {
            totalExp = Long.valueOf(10);
        } else if (level == 6) {
            totalExp = Long.valueOf(12);
        } else if (level == 7) {
            totalExp = Long.valueOf(14);
        } else if (level == 8) {
            totalExp = Long.valueOf(16);
        } else if (level == 9) {
            totalExp = Long.valueOf(18);
        } else if (level == 10) {
            totalExp = Long.valueOf(20);
        }
    }

    //레벨업 체크 메소드, 한번에 2레벨 이상의 레벨업을 할때도 처리
    public void checkLevelUp() {
        while (currentExp >= totalExp) {
            currentExp = currentExp - totalExp;
            LevelUp();
        }
    }

    // 10레벨에 경험치를 끝까지 채웠을 경우 식물의 상태를 완성으로 바꿈
    public void FullLevelCheck() {
        if (level == 10 && currentExp >= 20)
            status = Status.Complete;
    }

    // 물주기 횟수마다 식물의 totalWateringCount 증가
    public void plusTotalWateringCount (long count) {
        totalWateringCount+=count;
    }

}
