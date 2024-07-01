package org.smlions.comfortplant.domain.entity;


import jakarta.persistence.*;
import lombok.*;
import org.smlions.comfortplant.dto.UpdateUserEmailRequestDTO;
import org.smlions.comfortplant.dto.UpdateUserNicknameRequestDTO;
import org.smlions.comfortplant.dto.UpdateUserPasswordRequestDTO;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nickname;

    @Column
    private String password;

    @Column
    private String email;

    @Column
    private Long coin;

    @Column
    private Long wateringCount;

    @Column
    private String roles;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Plant> plant;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Watering> watering;

    public void updatePassword(UpdateUserPasswordRequestDTO updateUserPasswordRequestDTO){
        password= updateUserPasswordRequestDTO.getNewPassword();
    }

    public void updateNickname(UpdateUserNicknameRequestDTO updateUserNicknameRequestDTO){
        nickname = updateUserNicknameRequestDTO.getNewNickname();
    }

    public void updateEmail(UpdateUserEmailRequestDTO updateUserEmailRequestDTO){
        email = updateUserEmailRequestDTO.getNewEmail();
    }

    //물주기 횟수마다 코인 증가
    public void plusCoin(long count){
        coin += count;
    }

    // 스트레스 해소 활동 체크 할때마다 물주기 횟수 증가
    public void plusWateringCount(){
        wateringCount += 1;
    }
    // 스트레스 해소 활동 체크 취소 할때마다 물주기 횟수 감소
    public void minusWateringCount() {
        wateringCount -= 1;
    }

}
