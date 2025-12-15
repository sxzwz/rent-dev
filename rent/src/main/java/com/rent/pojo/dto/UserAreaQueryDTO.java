package com.rent.pojo.dto;

/**
 * 用户常居住地查询条件类
 */

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserAreaQueryDTO extends QueryDTO {

    //用户ID
    private Integer userId;
    //地区ID
    private Integer areaId;

}
