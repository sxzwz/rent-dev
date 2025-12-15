package com.rent.pojo.emum;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 流量类型枚举
 */
@Getter
@AllArgsConstructor
public enum FlowIndexEnum {

    VIEW(1, "浏览"),
    COLLECTION(2, "收藏"),
    REMAIN(3, "停留");

    /**
     * 类型
     */
    private final Integer type;
    /**
     * 描述
     */
    private final String detail;

}