package com.rent.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 地区信息表
 * </p>
 *
 * @author syk
 * @since 2025-12-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("area")

// 地区实体类
public class Area  {



    /**
     * 地区表主键ID，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 父级ID（构建省市区嵌套关系）
     */
    private Integer parentId;

    /**
     * 名称
     */
    private String name;


}
