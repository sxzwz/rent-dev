package com.rent.pojo.emum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否临近地铁枚举
 */
@Getter
@AllArgsConstructor
public enum IsSubwayEnum {

    STATUS_1(true, "临近"),
    STATUS_2(false, "非临近");

    private final Boolean type;
    private final String detail;

}