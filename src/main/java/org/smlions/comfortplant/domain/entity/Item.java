package org.smlions.comfortplant.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long itemId;

    @Column
    private String name;

    @Column
    private long price;

    @Column
    private String detail;

    @Column
    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;


    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;
}
