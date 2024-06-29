package org.smlions.comfortplant.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    Complete(0), Incomplete(1);

    int whatStatus;
}
