package org.smlions.comfortplant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@CrossOrigin(originPatterns = "*" ,value = "*")
@RequestMapping("/api/user")
@Tag(name = "회원 API", description = "회원 관련 API입니다.")
public class UserController {
    private final UserService userService;

    @Operation(method = "POST", summary = "회원가입", description = "회원 가입을 합니다. nickname, email, 체크가 끝난 password를 Body에 담아서 전송합니다.")
    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequestDto createUserRequestDto){
        UserResponseDTO userResponseDto = userService.createUser(createUserRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body("회원가입이 완료되었습니다. userId: " + userResponseDto.getId());
    }
    @Operation(method = "PUT", summary = "이메일 변경", description = "유저의 이메일을 변경합니다. header에 accessToken과 body에 password, newEamil을 담아서 전송합니다.")
    @PutMapping("/updateUserEmail")
    public ResponseEntity<?> updateUserEmail(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UpdateUserEmailRequestDTO updateUserEmailRequestDTO){
        UserResponseDTO userResponseDTO = userService.updateUserEmail(userDetails.getUsername(), updateUserEmailRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body("이메일 변경이 완료되었습니다.");
    }

    @Operation(method = "PUT", summary = "닉네임 변경", description = "유저의 닉네임을 변경합니다. header에 accessToken과 body에 newNickname을 담아서 전송합니다.")
    @PutMapping("/updateUserNickname")
    public ResponseEntity<?> updateUserNickname(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UpdateUserNicknameRequestDTO updateUserNicknameRequestDTO){
        UserResponseDTO userResponseDTO = userService.updateUserNickname(userDetails.getUsername(), updateUserNicknameRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body("닉네임 변경이 완료되었습니다.");
    }

    @Operation(method = "PUT", summary = "비밀번호 변경", description = "유저의 비밀번호를 변경합니다. header에 accessToken과 body에 password와 newPassword를 담아서 전송합니다.")
    @PutMapping("/updateUserPassword")
    public ResponseEntity<?> updateUserPassword(@AuthenticationPrincipal UserDetails userDetails, @RequestBody UpdateUserPasswordRequestDTO updateUserPasswordRequestDTO){
        UserResponseDTO userResponseDTO = userService.updateUserPassword(userDetails.getUsername(), updateUserPasswordRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body("비밀번호 변경이 완료되었습니다.");
    }

    @Operation(method = "DELETE", summary = "회원 삭제", description = "회원을 삭제합니다. header에 accessToken을 담아서 전송합니다.")
    @DeleteMapping("")
    public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal UserDetails userDetails){
        userService.deleteUser(userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }


    @Operation(method = "GET", summary = "비밀번호 찾기", description = "비밀번호를 재설정합니다. email과 체크된 newPassword를 body에 담아서 전송합니다.")
    @GetMapping("/findpassword")
    public ResponseEntity<?> findPassword(@RequestBody FindPasswordRequestDTO findPasswordRequestDTO){
        UserResponseDTO userResponseDTO = userService.findPassword(findPasswordRequestDTO);
        return ResponseEntity.status(HttpStatus.OK).body("비밀번호 재설정이 완료되었습니다.");
    }

    @Operation(method = "GET", summary = "유저 조회", description = "유저를 조회합니다. accessToken을 header에 담고, url parameter에 userId를 담아서 전송합니다.")
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserByUserId(userId));
    }

    @Operation(method = "GET", summary = "유저 코인 개수 조회", description = "유저의 코인 개수를 조회합니다. accessToken을 header에 담고, url parameter에 userId를 담아서 전송합니다.")
    @GetMapping("/{userId}/coin")
    public ResponseEntity<?> getCoin(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long userId){
        UserResponseDTO userResponseDTO = userService.getUserByUserId(userId);
        return ResponseEntity.status(HttpStatus.OK).body("코인 : " + userResponseDTO.getCoin());
    }

    @Operation(method = "POST", summary = "로그인", description = "로그인합니다. email과 password를 body에 담아서 전송합니다.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO loginRequestDTO){
        return null;
    }

    @Operation(method = "POST", summary = "로그아웃", description = "로그아웃합니다. accessToken을 header에 담아서 전송합니다. ")
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@AuthenticationPrincipal UserDetails userDetails) {
        return null;
    }
}
