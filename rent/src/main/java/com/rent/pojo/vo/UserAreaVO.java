package com.rent.pojo.vo;


import lombok.Data;

/**
 * 用户常住地VO类
 */
@Data
public class UserAreaVO {

    private Integer id;
    // 省份ID
    private Integer topAreaId;
    // 省份名
    private String topAreaName;
    // 市区ID
    private Integer cityAreaId;
    // 市区名称
    private String cityAreaName;
}
