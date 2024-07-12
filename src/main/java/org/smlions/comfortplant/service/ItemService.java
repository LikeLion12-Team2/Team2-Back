package org.smlions.comfortplant.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.smlions.comfortplant.domain.entity.*;
import org.smlions.comfortplant.dto.ItemDetailResDto;
import org.smlions.comfortplant.dto.ItemReqDto;
import org.smlions.comfortplant.dto.ItemResDto;
import org.smlions.comfortplant.repository.ItemRepository;
import org.smlions.comfortplant.repository.PlantRepository;
import org.smlions.comfortplant.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;
    private final PlantRepository plantRepository;

    public void initDefaultItems(){
        List<Item> defaultItems = Arrays.asList(
                new Item(0, "빨간색", 40, "산뜻한 빨간색 식물의 집을 꾸며보세요.\n" +
                        "조금 더 화사해진 색이 분명 맘에 쏙 드실거에요.", ItemStatus.COLOR, null),
                new Item(0, "파란색", 40, "산뜻한 파란색 식물의 집을 꾸며보세요.\n" +
                        "조금 더 화사해진 색이 분명 맘에 쏙 드실거에요.", ItemStatus.COLOR, null),
                new Item(0, "태양", 40, "타오르는 태양을 식물에게 선물해주세요!\n", ItemStatus.BACKGROUND, null),
                new Item(0, "창문", 40, "바람이 선선히 들어오는 창문", ItemStatus.BACKGROUND, null)
        );
        itemRepository.saveAll(defaultItems);
    }




    public List<ItemResDto> getItemList(){
        if(itemRepository.count() == 0){
            initDefaultItems();
        }
        List<Item> items = itemRepository.findAll();
        List<ItemResDto> itemResDtos = new ArrayList<>();
        for(Item item : items){
            ItemResDto itemResDto = new ItemResDto().from(item);
            itemResDtos.add(itemResDto);
        }

        return itemResDtos;
    }

    public void buyItem(ItemReqDto itemReqDto, String email){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        Item item = itemRepository.findById(itemReqDto.getItemId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이템입니다."));
        Plant plant = plantRepository.findById(itemReqDto.getPlantId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이템입니다."));


        if(item.getItemStatus().equals(ItemStatus.COLOR)){
            plant.setColorItem(item.getName());
        } else if (item.getItemStatus().equals(ItemStatus.BACKGROUND)){
            plant.setColorItem(item.getName());
        }
        user.buyItem(item);
        plantRepository.save(plant);
    }

    public ItemDetailResDto getItem(long itemId){
        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("아이템을 찾을 수 없습니다."));

        return new ItemDetailResDto().from(item);
    }
}
