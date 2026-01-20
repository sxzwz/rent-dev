package com.rent.pojo.emum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 房屋租赁类型枚举
 */
@Getter
@AllArgsConstructor
public enum RentalTypeEnum {

    TOTAL(1, "整租"),
    TOGETHER(2, "合租");

    /**
     * 类型
     */
    private final Integer type;
    /**
     * 描述
     */
    private final String detail;

}