package org.smlions.comfortplant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.smlions.comfortplant.domain.entity.User;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class UserResponseDTO {
    public Long id;
    public String nickname;
    public String email;

    public Long coin;

    public static UserResponseDTO from(User user){
        return UserResponseDTO.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .coin(user.getCoin())
                .build();
    }
}
