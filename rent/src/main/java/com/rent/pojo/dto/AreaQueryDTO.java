package com.rent.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 地区查询条件
 */

@Data
@EqualsAndHashCode(callSuper = true)
public class AreaQueryDTO extends QueryDTO {

    // 父级id
    private Integer parentId;
    // 地区名
    private String Name;
}
