package org.smlions.comfortplant.dto;


import jakarta.persistence.Id;
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
public class ItemResDto {
    private long itemId;
    private String name;
    private ItemStatus itemStatus;
    private long price;

    public ItemResDto from(Item item){
        return ItemResDto.builder()
                .itemId(item.getItemId())
                .name(item.getName())
                .itemStatus(item.getItemStatus())
                .price(item.getPrice())
                .build();
    }
}
