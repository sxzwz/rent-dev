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
 * 小区信息表
 * </p>
 *
 * @author syk
 * @since 2025-12-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("community")

// 小区实体类
public class Community {

    /**
     * 小区信息表主键ID，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 地区ID，外键，标识的是小区在哪个地区下
     */
    private Integer areaId;

    /**
     * 小区名
     */
    private String name;

    /**
     * 封面
     */
    private String cover;

    /**
     * 小区实况图
     */
    private String covers;

    /**
     * 小区描述信息
     */
    private String detail;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


}
