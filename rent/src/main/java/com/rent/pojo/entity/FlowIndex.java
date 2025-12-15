package com.rent.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 流量信息表
 * </p>
 *
 * @author syk
 * @since 2025-12-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("flow_index")

public class FlowIndex  {


    /**
     * 流量指标信息表主键ID，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 流量类型（1：浏览；2：收藏;；3：停留时长）
     */
    private Integer type;

    /**
     * 停留时长（毫秒）
     */
    private Integer times;

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

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


}
