package com.rent.pojo.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CommunityQueryDTO extends QueryDTO {

    // 小区名字
    private String name;
    // 小区所属地ID
    private Integer areaId;

}
