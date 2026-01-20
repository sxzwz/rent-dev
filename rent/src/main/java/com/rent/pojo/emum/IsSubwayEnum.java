package com.rent.pojo.emum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否临近地铁枚举
 */
@Getter
@AllArgsConstructor
public enum IsSubwayEnum {

    STATUS_1(1, "临近"),
    STATUS_2(0, "非临近");

    private final Integer type;
    private final String detail;

}