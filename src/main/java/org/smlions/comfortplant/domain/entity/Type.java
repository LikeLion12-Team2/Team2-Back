package org.smlions.comfortplant.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Type {
    TREE(0), FLOWER(1);

    int status;
}
