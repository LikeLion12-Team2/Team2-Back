package org.smlions.comfortplant.service;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.smlions.comfortplant.domain.entity.User;
import org.smlions.comfortplant.dto.*;
import org.smlions.comfortplant.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final ActivityService activityService;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Transactional
    public UserResponseDTO createUser(CreateUserRequestDto createUserRequestDto){

        if(userRepository.existsByEmail(createUserRequestDto.getEmail()))
            throw new IllegalArgumentException("이미 존재하는 이메일입니다.");

        User user = createUserRequestDto.toEntity(passwordEncoder);

        user.setWateringCount(Long.valueOf(0));

        user.setCoin(Long.valueOf(0));
        userRepository.save(user);
        activityService.addDefaultActivitiesToUser(user);
        return UserResponseDTO.from(user);

    }

    @Transactional
    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));

        userRepository.delete(user);
    }


   @Transactional
   public UserResponseDTO findPassword(FindPasswordRequestDTO findPasswordRequestDTO){
       User user = userRepository.findByEmail(findPasswordRequestDTO.getEmail()).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));
       user.updatePassword(passwordEncoder.encode(findPasswordRequestDTO.getNewPassword()));

       return UserResponseDTO.from(user);

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
    public UserResponseDTO updateUserPassword(String email, UpdateUserPasswordRequestDTO updateUserPasswordRequestDTO) {

        User user = userRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));


        if (!passwordEncoder.matches(updateUserPasswordRequestDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        if (passwordEncoder.matches(updateUserPasswordRequestDTO.getNewPassword(), user.getPassword())) {
            throw new IllegalArgumentException("기존 비밀번호와 일치합니다.");
        }

        user.updatePassword(passwordEncoder.encode(updateUserPasswordRequestDTO.getNewPassword()));

        userRepository.save(user);

        return UserResponseDTO.from(user);
    }

    @Transactional
    public UserResponseDTO updateUserNickname(String email, UpdateUserNicknameRequestDTO updateUserNicknameRequestDTO) {
        User user = userRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));

        user.updateNickname(updateUserNicknameRequestDTO);

        userRepository.save(user);

        return UserResponseDTO.from(user);

    }

    @Transactional
    public UserResponseDTO updateUserEmail(String email, UpdateUserEmailRequestDTO updateUserEmailRequestDTO){

        //인증 인가 구현 때 수정
        User user = userRepository.findByEmail(email).orElseThrow(()-> new IllegalArgumentException("존재하지 않는 회원입니다."));

        if (!passwordEncoder.matches(updateUserEmailRequestDTO.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        user.updateEmail(updateUserEmailRequestDTO);

        userRepository.save(user);

        return UserResponseDTO.from(user);

    }


}
