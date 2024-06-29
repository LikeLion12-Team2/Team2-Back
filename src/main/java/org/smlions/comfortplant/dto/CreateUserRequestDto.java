package org.smlions.comfortplant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.smlions.comfortplant.domain.entity.User;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateUserRequestDto {

    public String nickname;
    public String password;
    public String email;

    public User toEntity() {
        return User.builder()
                .nickname(nickname)
                .password(password)
                .email(email)
                .build();
    }
}
