package com.rent.pojo.emum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 房屋装修状态枚举
 */
@Getter
@AllArgsConstructor
public enum HouseFitmentEnum {

    HOUSE_FITMENT_1(1, "毛坯房"),
    HOUSE_FITMENT_2(2, "简装"),
    HOUSE_FITMENT_3(3, "精装修");

    /**
     * 类型
     */
    private final Integer type;
    /**
     * 描述
     */
    private final String detail;

}