package org.smlions.comfortplant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.smlions.comfortplant.domain.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CreateUserRequestDto {

    public String nickname;
    public String password;
    public String email;

    public User toEntity(PasswordEncoder passwordEncoder) {
        String encodePassword = passwordEncoder.encode(password);

        return User.builder()
                .nickname(nickname)
                .password(password)
                .email(email)
                .password(encodePassword)
                .roles("USER")
                .build();
    }
}
