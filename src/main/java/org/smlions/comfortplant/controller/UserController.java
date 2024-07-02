package org.smlions.comfortplant.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smlions.comfortplant.dto.*;
import org.smlions.comfortplant.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequestDto createUserRequestDto){
        UserResponseDTO userResponseDto = userService.createUser(createUserRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("회원가입이 완료되었습니다. userId: " + userResponseDto.getId());
    }

    @PutMapping("/updateUserEmail")
    public ResponseEntity<?> updateUserEmail(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UpdateUserEmailRequestDTO updateUserEmailRequestDTO){
        UserResponseDTO userResponseDTO = userService.updateUserEmail(userDetails.getUsername(), updateUserEmailRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body("이메일 변경이 완료되었습니다.");
    }

    @PutMapping("/updateUserNickname")
    public ResponseEntity<?> updateUserNickname(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UpdateUserNicknameRequestDTO updateUserNicknameRequestDTO){
        UserResponseDTO userResponseDTO = userService.updateUserNickname(userDetails.getUsername(), updateUserNicknameRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body("닉네임 변경이 완료되었습니다.");
    }

    @PutMapping("/updateUserPassword")
    public ResponseEntity<?> updateUserPassword(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UpdateUserPasswordRequestDTO updateUserPasswordRequestDTO){
        UserResponseDTO userResponseDTO = userService.updateUserPassword(userDetails.getUsername(), updateUserPasswordRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body("비밀번호 변경이 완료되었습니다.");
    }

    @DeleteMapping("")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal UserDetails userDetails){
        userService.deleteUser(userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/findpassword")
    public ResponseEntity<?> findPassword(@RequestBody FindPasswordRequestDTO findPasswordRequestDTO){
        UserResponseDTO userResponseDTO = userService.findPassword(findPasswordRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body("비밀번호 재설정이 완료되었습니다.");
    }

    @GetMapping("")
    public ResponseEntity<?> getUser(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserByUserId(userId));
    }
}
