package com.rent.pojo.emum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 房屋预约看房状态枚举
 */
@Getter
@AllArgsConstructor
public enum HouseOrderStatusEnum {

    STATUS_1(1, "预约中"),
    STATUS_2(2, "已预约"),
    STATUS_3(3, "预约失败"),
    STATUS_4(4, "已取消"),
    STATUS_5(5, "已完成");

    /**
     * 类型
     */
    private final Integer type;
    /**
     * 描述
     */
    private final String detail;

}