package com.rent.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 房东信息查询条件类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class LandlordQueryDTO extends QueryDTO {

    // 用户ID
    private Integer userId;

    // 审核状态 (0:未审核 1:已审核)
    private Boolean isAudit;


}
