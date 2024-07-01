package org.smlions.comfortplant.controller;

import lombok.RequiredArgsConstructor;
import org.smlions.comfortplant.dto.ItemReqDto;
import org.smlions.comfortplant.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shop")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("")
    public ResponseEntity<?> itemList(){
        try{
            return ResponseEntity.ok(itemService.getItemList());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }
    }
    @PostMapping("/purchase")
    public ResponseEntity<?> buyItem(@RequestBody ItemReqDto itemReqDto){
        try{
            itemService.buyItem(itemReqDto);
            return ResponseEntity.status(HttpStatus.OK).body("아이템 구매가 완료되었습니다.\nitemId :"
                                                        + itemReqDto.getItemId()+ "\n"
                                                        + "platnId : " + itemReqDto.getPlantId());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }
    }


    @GetMapping("/item")
    public ResponseEntity<?> getItem(@RequestParam("itemId") long itemId){
        try{
            return ResponseEntity.ok(itemService.getItem(itemId));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }
    }
}
