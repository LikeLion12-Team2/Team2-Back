package org.smlions.comfortplant.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdateUserEmailRequestDTO {
    public Long id;
    public String password;
    public String newEmail;

}
