package org.smlions.comfortplant.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "wateringanddatedata")
public class WateringAndDateData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate data;

    @Column
    private Long totalWatering;

    @ManyToOne(fetch = FetchType.LAZY)
    private Plant plant;

    public void setDataWatering(LocalDate date, long total, Plant p){
        data = date;
        totalWatering= total;
        plant = p;
    }

}
