package org.smlions.comfortplant.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.smlions.comfortplant.domain.entity.Item;
import org.smlions.comfortplant.domain.entity.ItemStatus;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDetailResDto {
    private long itemId;
    private String name;
    private ItemStatus itemStatus;
    private long price;
    private String detail;

    public ItemDetailResDto from(Item item){
        return ItemDetailResDto.builder()
                .itemId(item.getItemId())
                .name(item.getName())
                .itemStatus(item.getItemStatus())
                .price(item.getPrice())
                .detail(item.getDetail())
                .build();
    }
}
