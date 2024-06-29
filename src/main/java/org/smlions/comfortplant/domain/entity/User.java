package org.smlions.comfortplant.domain.entity;


import jakarta.persistence.*;
import lombok.*;
import org.smlions.comfortplant.dto.UpdateUserEmailRequestDTO;
import org.smlions.comfortplant.dto.UpdateUserNicknameRequestDTO;
import org.smlions.comfortplant.dto.UpdateUserPasswordRequestDTO;

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

    public void updatePassword(UpdateUserPasswordRequestDTO updateUserPasswordRequestDTO){
        password= updateUserPasswordRequestDTO.getNewPassword();
    }

    public void updateNickname(UpdateUserNicknameRequestDTO updateUserNicknameRequestDTO){
        nickname = updateUserNicknameRequestDTO.getNewNickname();
    }

    public void updateEmail(UpdateUserEmailRequestDTO updateUserEmailRequestDTO){
        email = updateUserEmailRequestDTO.getNewEmail();
    }

}
