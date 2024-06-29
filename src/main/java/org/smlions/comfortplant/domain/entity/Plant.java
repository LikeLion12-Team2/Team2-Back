package org.smlions.comfortplant.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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

    public void setFirstTime(){
        currentExp = Long.valueOf(0);
        level = Long.valueOf(1);
        status = Status.Incomplete;
        totalExp = Long.valueOf(2);
        totalWateringCount = Long.valueOf(0);
    }
}
