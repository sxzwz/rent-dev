package com.rent.pojo.emum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 房屋押金枚举
 */
@Getter
@AllArgsConstructor
public enum HouseDepositEnum {

    HOUSE_SIZED_1(1, "押一付一"),
    HOUSE_SIZED_2(2, "押一付二"),
    HOUSE_SIZED_3(3, "押一付三"),
    HOUSE_SIZED_4(4, "押二付三");

    /**
     * 类型
     */
    private final Integer type;
    /**
     * 描述
     */
    private final String detail;

}