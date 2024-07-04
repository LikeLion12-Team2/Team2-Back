package org.smlions.comfortplant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.smlions.comfortplant.dto.CreateActivityReqDto;
import org.smlions.comfortplant.dto.UpdateActivityReqDto;
import org.smlions.comfortplant.service.ActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/activity")
@Tag(name = "스트레스 해소 활동 API", description = "스트레스 해소 활동 관련 API입니다.")
public class ActivityController {
    private final ActivityService activityService;

    // 활동 목록 조회
    @Operation(method = "GET", summary = "활동 목록 조회", description = "로그인한 유저의 활동 목록을 조회합니다. List<ActivityResDto>를 반환합니다.")
    @GetMapping("")
    public ResponseEntity<?> activityList(@AuthenticationPrincipal UserDetails userDetails){
        try{
            return ResponseEntity.ok(activityService.getActivityList(userDetails.getUsername()));
        } catch (NoSuchElementException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 유저이거나, 식물입니다.");
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }
    }

    // 활동 체크
    @Operation(method = "POST", summary = "활동 체크", description = "로그인한 유저의 특정 activityId에 해당하는 활동을 체크합니다. RequestParam로 activityId를 입력합니다. 물주기 횟수를 증가시키고 반환합니다.")
    @PostMapping("/check")
    public ResponseEntity<?> activityCheck(@RequestParam("activityId") long activityId,
                                           @AuthenticationPrincipal UserDetails userDetails) {
        try {
            long count = activityService.checkActivity(activityId, userDetails.getUsername());
            return ResponseEntity.status(HttpStatus.OK).body("물주기 횟수가 1 증가 했습니다.\nwateringCount: " + count);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 유저이거나 식물입니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }
    }

    @Operation(method = "POST", summary = "활동 체크 풀기", description = "로그인한 유저의 특정 activityId에 해당하는 활동을 체크를 풉니다. RequestParam로 activityId를 입력합니다. 물주기 횟수를 감소시키고 반환합니다.")
    @PostMapping("/uncheck")
    public ResponseEntity<?> activityUnheck(@RequestParam("activityId") long activityId,
                                            @AuthenticationPrincipal UserDetails userDetails) {
        try {
            long count = activityService.uncheckActivity(activityId, userDetails.getUsername());
            return ResponseEntity.status(HttpStatus.OK).body("물주기 횟수가 1 감소 했습니다.\nwateringCount: " + count);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 유저이거나 식물입니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }
    }


    // 활동 추가
    @Operation(method = "POST", summary = "활동 추가", description = "로그인한 유저의 활동을 createActivityReqDto형태로 body에 담아 추가합니다. 해당 activityId를 반환합니다.")
    @PostMapping("/create")
    public  ResponseEntity<?> activityCreate(@RequestBody CreateActivityReqDto createActivityReqDto,
                                             @AuthenticationPrincipal UserDetails userDetails){
        try{
            Long activityId = activityService.createActivity(createActivityReqDto, userDetails.getUsername());
            return ResponseEntity.status(HttpStatus.CREATED).body("스트레스 해소 활동 생성 완료되었습니다. \nactivity id : " + activityId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }
    }

    // 활동 수정
    @Operation(method = "PUT", summary = "활동 수정", description = "로그인한 유저의 활동을 UpdateActivityReqDto형태로 담아 body에 담아 수정합니다.")
    @PutMapping("")
    public ResponseEntity<?> activityUpdate(@RequestBody UpdateActivityReqDto updateActivityReqDto,
                                            @AuthenticationPrincipal UserDetails userDetails){
        try{
            activityService.updateActivity(updateActivityReqDto, userDetails.getUsername());
            return ResponseEntity.status(HttpStatus.OK).body("스트레스 해소 활동 수정이 완료되었습니다.");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 유저, 식물, 스트레스 해소 활동입니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("기본으로 제공되는 스트레스 해소 활동은 수정할 수 없습니다..");
        }
    }

    @Operation(method = "DELETE", summary = "활동 삭제", description = "로그인한 유저의 활동을 RequestParam에 activityId를 같이 보내서 삭제합니다.")
    @DeleteMapping("")
    public ResponseEntity<?> activityDelete(@RequestParam("activityId") long activityId,
                                            @AuthenticationPrincipal UserDetails userDetails){
        try{
            activityService.deleteActivity(activityId, userDetails.getUsername());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("스트레스 해소 활동 삭제가 완료되었습니다.");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("존재하지 않는 유저, 식물, 스트레스 해소 활동입니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("기본으로 제공되는 스트레스 해소 활동은 수정할 수 없습니다.");
        }
    }
}



