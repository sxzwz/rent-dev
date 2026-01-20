package com.rent.pojo.vo;

import com.rent.pojo.entity.Community;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 小区信息VO类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class CommunityVO extends Community {


    /**
     * 省份ID
     */
    private Integer topAreaId;

    /**
     * 省份名
     */
    private String topAreaName;
    /**
     * 市区名
     */
    private String cityAreaName;
}
