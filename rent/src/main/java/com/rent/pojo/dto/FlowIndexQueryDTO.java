package com.rent.pojo.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 流量指标信息查询条件类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class FlowIndexQueryDTO extends QueryDTO{
    /**
     * 流量类型（1：浏览；2：收藏；3：停留）
     */
    private Integer type;
    /**
     * 内容模块（标识的是该流量属于哪一个模块下面）
     */
    private String contentType;
    /**
     * 内容ID（标识的是该模块下面的哪一个内容的ID）
     */
    private Integer contentId;
    /**
     * 用户ID
     */
    private Integer userId;
}