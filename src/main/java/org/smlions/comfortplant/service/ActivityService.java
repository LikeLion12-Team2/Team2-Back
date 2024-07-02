package org.smlions.comfortplant.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smlions.comfortplant.domain.entity.Activity;
import org.smlions.comfortplant.domain.entity.ActivityStatus;
import org.smlions.comfortplant.domain.entity.User;
import org.smlions.comfortplant.dto.CreateActivityReqDto;
import org.smlions.comfortplant.dto.ActivityResDto;
import org.smlions.comfortplant.dto.UpdateActivityReqDto;
import org.smlions.comfortplant.repository.ActivityRepository;
import org.smlions.comfortplant.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ActivityService {
    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    // 이 메소드는 UserService의 유저 생성 부분에 추가할 예정.
    public void addDefaultActivitiesToUser(User user){
        List<Activity> defaultActivities = Arrays.asList(
                new Activity(0, "스트레스 해소 활동 목록", ActivityStatus.DEFAULT, false, user),
                new Activity(0, "짧은 산책하기", ActivityStatus.DEFAULT, false, user),
                new Activity(0, "숙면하기", ActivityStatus.DEFAULT, false, user),
                new Activity(0, "명상하기", ActivityStatus.DEFAULT, false, user),
                new Activity(0, "긍정적으로 생각하기", ActivityStatus.DEFAULT, false, user)
        );
        activityRepository.saveAll(defaultActivities);
    }

    public List<ActivityResDto> getActivityList(String email){

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 유저입니다."));

        List<Activity> activities = activityRepository.findActivities(email);
        List<ActivityResDto> activityResDtos = new ArrayList<>();

        for(Activity activity : activities){
            ActivityResDto activityResDto = new ActivityResDto().from(activity);
            activityResDtos.add(activityResDto);
        }

        return activityResDtos;
    }

    public Long createActivity(CreateActivityReqDto createActivityReqDto, String email){
        try {
            User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
            Activity activity = createActivityReqDto.toEntity();
            activity.setUser(user);
            activityRepository.save(activity);
            return activity.getActivityId();
        }catch (Exception e){
            throw new IllegalArgumentException("User를 찾을 수 없습니다. ");
        }
    }


    // 수정 필요!
    public long checkActivity(long activityId, String email) {
        try {
            Activity activity = activityRepository.findById(activityId)
                    .orElseThrow(() -> new NoSuchElementException("존재하지 않는 활동입니다."));
            if(!activity.getUser().getEmail().equals(email)){
                throw new AccessDeniedException("로그인한 유저와 활동의 유저가 다릅니다.");
            }
            // 나머지 로직은 그대로 유지
            activity.check();
            User user = activity.getUser();
            user.plusWateringCount();
            return user.getWateringCount();
        } catch (NoSuchElementException e) {
            throw new NoSuchElementException("활동을 찾을 수 없습니다.");
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
    }


    public long uncheckActivity(long activityId, String email){
        try {
            Activity activity = activityRepository.findById(activityId)
                    .orElseThrow(() -> new NoSuchElementException("존재하지 않는 활동입니다."));

            if(!activity.getUser().getEmail().equals(email)){
                throw new AccessDeniedException("로그인한 유저와 활동의 유저가 다릅니다.");
            }
            // 나머지 로직은 그대로 유지
            activity.uncheck();
            User user = activity.getUser();
            user.minusWateringCount();
            return user.getWateringCount();
        } catch (NoSuchElementException e) {
            throw new IllegalArgumentException("활동을 찾을 수 없습니다.");
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateActivity(UpdateActivityReqDto updateActivityReqDto, String email) {
        try{
            Activity activity = activityRepository.findById(updateActivityReqDto.getActivityId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 활동입니다."));
            if(activity.getActivityStatus().equals(ActivityStatus.DEFAULT)){
                throw new IllegalStateException("ADD상태인 활동만 삭제가능합니다.");
            }
            if(!activity.getUser().getEmail().equals(email)){
                throw new AccessDeniedException("로그인한 유저와 활동의 유저가 다릅니다.");
            }
            activity.update(updateActivityReqDto);
            activityRepository.save(activity);
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("User를 찾을 수 없습니다. ");
        } catch (Exception e){
            throw new NoSuchElementException("타입을 찾을 수 없습니다. ");

        }
    }

    public void deleteActivity(long activityId, String email){
        try{
            Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 활동입니다."));
            if(activity.getActivityStatus().equals(ActivityStatus.DEFAULT)){
                throw new IllegalStateException("ADD상태인 활동만 삭제가능합니다.");
            }
            if(!activity.getUser().getEmail().equals(email)){
                throw new AccessDeniedException("로그인한 유저와 활동의 유저가 다릅니다.");
            }
            activityRepository.deleteById(activityId);
        }catch (IllegalStateException e){
            throw new IllegalArgumentException("활동을 찾을 수 없습니다. ");
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
    }
}
