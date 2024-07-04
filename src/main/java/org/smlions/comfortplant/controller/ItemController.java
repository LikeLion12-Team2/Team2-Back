package org.smlions.comfortplant.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.smlions.comfortplant.dto.ItemReqDto;
import org.smlions.comfortplant.service.ItemService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shop")
@Tag(name = "아이템 API", description = "아이템 관련 API입니다.")
public class ItemController {

    private final ItemService itemService;

    @Operation(method = "GET", summary = "아이템 목록 조회", description = "전체 아이템 리스트를 List<ItemResDto>형태로 조회합니다. (인가 적용됨)")
    @GetMapping("")
    public ResponseEntity<?> itemList(@AuthenticationPrincipal UserDetails userDetails){
        try{
            return ResponseEntity.ok(itemService.getItemList());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }
    }

    @Operation(method = "POST", summary = "아이템 구매", description = "로그인한 상태로 ItemReqDto형태로 body에 담아 요청합니다. itemId와 plantId를 반환합니다.")
    @PostMapping("/purchase")
    public ResponseEntity<?> buyItem(@RequestBody ItemReqDto itemReqDto,
                                     @AuthenticationPrincipal UserDetails userDetails){
        try{
            itemService.buyItem(itemReqDto, userDetails.getUsername());
            return ResponseEntity.status(HttpStatus.OK).body("아이템 구매가 완료되었습니다.\nitemId :"
                    + itemReqDto.getItemId()+ "\n"
                    + "platnId : " + itemReqDto.getPlantId());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }
    }


    @Operation(method = "GET", summary = "아이템 상세 조회", description = "itemId를 RequestParam에 담아서 요청합니다. ItemDetailResDto형태로 Item을 반환합니다. (인가 적용됨)")
    @GetMapping("/item")
    public ResponseEntity<?> getItem(@RequestParam("itemId") long itemId, @AuthenticationPrincipal UserDetails userDetails){
        try{
            return ResponseEntity.ok(itemService.getItem(itemId));

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("잘못된 요청입니다.");
        }
    }
}
