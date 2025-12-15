package com.rent.pojo.entity;

import java.math.BigDecimal;
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
 * 房屋信息表
 * </p>
 *
 * @author syk
 * @since 2025-12-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("house")


public class House  {


    /**
     * 房屋信息主键ID，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 房屋名
     */
    private String name;

    /**
     * 房东ID，外键，关联的是房东信息表，标识是哪个房东发布的房屋信息
     */
    private Integer landlordId;

    /**
     * 地区ID，外键，关联的是地区信息表，标识房屋信息从属于哪个地区
     */
    private Integer areaId;

    /**
     * 小区ID，外键，关联的是小区信息表，标识的是房屋从属于哪个小区
     */
    private Integer communityId;

    /**
     * 房屋描述
     */
    private String detail;

    /**
     * 封面
     */
    private String cover;

    /**
     * 房屋实况图列表
     */
    private String covers;

    /**
     * 房屋类型ID，外键，比如商品房
     */
    private Integer typeId;

    /**
     * 面积
     */
    private Double sizeNumber;

    /**
     * 方向ID，外键，比如坐北朝南
     */
    private Integer directionId;

    /**
     * 楼层
     */
    private String floor;

    /**
     * 户型ID，外键，比如一室一厅
     */
    private Integer sizedId;

    /**
     * 租金
     */
    private BigDecimal rent;

    /**
     * 押金方式ID，外键，比如押一付一
     */
    private Integer depositMethodId;

    /**
     * 房屋状态（1：待租；2：下架）
     */
    private Integer status;

    /**
     * 是否临近地铁（0：非临近；1：临近）
     */
    private Boolean isSubway;

    /**
     * 临近地铁线路（如果进地铁，这个值才需要设置）
     */
    private Integer subwayLine;

    /**
     * 装修状态ID，外键，比如毛坯房
     */
    private Integer fitmentStatusId;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


}
