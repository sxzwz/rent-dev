package com.rent.pojo.vo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 房屋列表VO类
 */
@Data
public class HouseListItemVO {
    /**
     * 房屋ID
     */
    private Integer id;
    /**
     * 房屋名
     */
    private String name;
    /**
     * 房屋封面
     */
    private String cover;
    /**
     * 面积
     */
    private Double sizeNumber;
    /**
     * 所属小区
     */
    private String communityName;
    /**
     * 所属市区
     */
    private String cityAreaName;
    /**
     * 朝向ID
     */
    private Integer directionId;
    /**
     * 朝向名
     */
    private String directionName;
    /**
     * 装修状态ID
     */
    private Integer fitmentStatusId;
    /**
     * 装修状态，比如毛坯房
     */
    private String fitmentStatusName;
    /**
     * 租金
     */
    private BigDecimal rent;
    /**
     * 押金方式ID
     */
    private Integer depositMethodId;
    /**
     * 押金方式，比如押一付一
     */
    private String depositMethodName;
}