package org.smlions.comfortplant.controller;

import lombok.RequiredArgsConstructor;
import org.smlions.comfortplant.dto.CreateActivityReqDto;
import org.smlions.comfortplant.dto.UpdateActivityReqDto;
import org.smlions.comfortplant.service.ActivityService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/activity")
public class ActivityController {
    private final ActivityService activityService;



    // 활동 목록 조회
    @GetMapping("")
    public ResponseEntity<?> activityList(@RequestParam("email") String email){
        try{
            return ResponseEntity.ok(activityService.getActivityList(email));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }
    }

    // 활동 체크
    @PostMapping("/check")
    public ResponseEntity<?> activityCheck(@RequestParam("activityId") long activityId) {
        try {
            long count = activityService.checkActivity(activityId);
            return ResponseEntity.status(HttpStatus.OK).body("물주기 횟수가 1 증가 했습니다.\nwateringCount: " + count);
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("NoSuchElementException: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }

    @PostMapping("/uncheck")
    public ResponseEntity<?> activityUnheck(@RequestParam("activityId") long activityId) {
        try {
            long count = activityService.uncheckActivity(activityId);
            return ResponseEntity.status(HttpStatus.OK).body("물주기 횟수가 1 감소 했습니다.\nwateringCount: " + count);
        } catch (IllegalArgumentException e) {
            System.out.println("IllegalArgumentException: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (NoSuchElementException e) {
            System.out.println("NoSuchElementException: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            System.out.println("Exception: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류가 발생했습니다.");
        }
    }


    // 활동 추가
    @PostMapping("/create")
    public  ResponseEntity<?> activityCreate(@RequestBody CreateActivityReqDto createActivityReqDto,
                                             @RequestParam("email") String email){
        try{
            Long activityId = activityService.createActivity(createActivityReqDto, email);
            return ResponseEntity.status(HttpStatus.CREATED).body("스트레스 해소 활동 생성 완료되었습니다. 활동 id : " + activityId);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }
    }

    // 활동 수정
    @PutMapping("")
    public ResponseEntity<?> activityUpdate(@RequestBody UpdateActivityReqDto updateActivityReqDto){
        try{
            activityService.updateActivity(updateActivityReqDto);
            return ResponseEntity.status(HttpStatus.OK).body("스트레스 해소 활동 수정이 완료되었습니다.");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("기본으로 제공되는 스트레스 해소 활동은 수정할 수 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("서버 오류가 발생했습니다.");
        }
    }

    @DeleteMapping("")
    public ResponseEntity<?> activityDelete(@RequestParam("activityId") long activityId){
        try{
            activityService.deleteActivity(activityId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("스트레스 해소 활동 삭제가 완료되었습니다.");
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("기본으로 제공되는 스트레스 해소 활동은 수정할 수 없습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("서버 오류가 발생했습니다.");
        }
    }
}



