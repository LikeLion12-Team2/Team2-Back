package org.smlions.comfortplant.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smlions.comfortplant.domain.entity.Plant;
import org.smlions.comfortplant.domain.entity.User;
import org.smlions.comfortplant.dto.CreatePlantRequestDTO;
import org.smlions.comfortplant.dto.CreateWateringRequestDTO;
import org.smlions.comfortplant.dto.PlantResponseDTO;
import org.smlions.comfortplant.repository.PlantRepository;
import org.smlions.comfortplant.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WateringService {
    private final PlantRepository plantRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createWatering(String email, CreateWateringRequestDTO createWateringRequestDTO){

        Optional<Plant> plant = plantRepository.findById(createWateringRequestDTO.getPlantId());
        Optional<User> user = userRepository.findByEmail(email);

        //wateringCount 횟수만큼 식물 경험치 증가
        plant.get().plusCurrentExp(user.get().getWateringCount());

        //물주기를 했을 때 식물 경험치 및 레벨에 따라서 상태 변화
        plant.get().CheckComplete();

        //wateringCount 횟수만큼 유저 코인 증가
        user.get().plusCoin(user.get().getWateringCount());

        //한번 물주기가 끝난 후 유저의 wateringCount는 0으로 초기화
        user.get().setWateringCount(Long.valueOf(0));

        //물주기를 할때마다 plant의 totalWateringCount 증가
        plant.get().plusTotalWateringCount(user.get().getWateringCount());


    }
}
