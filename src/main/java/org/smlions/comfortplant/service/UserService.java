package org.smlions.comfortplant.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.smlions.comfortplant.domain.entity.User;
import org.smlions.comfortplant.dto.*;
import org.smlions.comfortplant.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public UserResponseDTO createUser(CreateUserRequestDto createUserRequestDto){

        if(userRepository.existsByEmail(createUserRequestDto.getEmail()))
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");

        User user = createUserRequestDto.toEntity(passwordEncoder);

        user.setWateringCount(Long.valueOf(0));

        user.setCoin(Long.valueOf(0));
        userRepository.save(user);
        return UserResponseDTO.from(user);

    }

    @Transactional
    public void deleteUser(Long userId) {
        log.info("[User Service] 유저 삭제하기 ID ---> {}", userId);
        userRepository.deleteById(userId);
    }


   @Transactional
   public String findPassword(FindPasswordRequestDTO findPasswordRequestDTO){
       User user = userRepository.findByEmail(findPasswordRequestDTO.getEmail()).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));
       return "Password : "+ user.getPassword();
   }
    public UserResponseDTO getUserByEmail(String email){
        User user = userRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));
        return UserResponseDTO.from(user);
    }

    public UserResponseDTO getUserByPassword(String password){
        User user = userRepository.findByPassword(password).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));
        return UserResponseDTO.from(user);
    }

    public UserResponseDTO getUserByUserId(Long id) {
        User user = userRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));
        return UserResponseDTO.from(user);
    }

    @Transactional
    public UserResponseDTO updateUserPassword(UpdateUserPasswordRequestDTO updateUserPasswordRequestDTO) {
        // 기존 비밀번호로 user 확인


        User user = userRepository.findById(updateUserPasswordRequestDTO.getId()).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (!user.getPassword().equals(updateUserPasswordRequestDTO.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        if (user.getPassword().equals(updateUserPasswordRequestDTO.getNewPassword())) {
            throw new IllegalArgumentException("기존 비밀번호와 일치합니다.");
        }

        user.updatePassword(updateUserPasswordRequestDTO);

        userRepository.save(user);

        return UserResponseDTO.from(user);
    }

    @Transactional
    public UserResponseDTO updateUserNickname(UpdateUserNicknameRequestDTO updateUserNicknameRequestDTO) {
        User user = userRepository.findById(updateUserNicknameRequestDTO.getId()).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));

        user.updateNickname(updateUserNicknameRequestDTO);

        userRepository.save(user);

        return UserResponseDTO.from(user);

    }

    @Transactional
    public UserResponseDTO updateUserEmail(UpdateUserEmailRequestDTO updateUserEmailRequestDTO){

        //인증 인가 구현 때 수정
        User user = userRepository.findById(updateUserEmailRequestDTO.getId()).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (!user.getPassword().equals(updateUserEmailRequestDTO.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        user.updateEmail(updateUserEmailRequestDTO);

        userRepository.save(user);

        return UserResponseDTO.from(user);

    }


}
