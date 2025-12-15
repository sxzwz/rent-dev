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
 * 房东信息表
 * </p>
 *
 * @author syk
 * @since 2025-12-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("landlord")


public class Landlord {



    /**
     * 房东信息表主键ID，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 申请人ID，外键，关联的是用户表，标识的是哪个用户申请成为的房东
     */
    private Integer userId;

    /**
     * 身份证正面照
     */
    private String idcardFront;

    /**
     * 身份证反面照
     */
    private String idcardOpposite;

    /**
     * 身份证号
     */
    private String idcard;

    /**
     * 是否已经审核（0：未审核；1：已审核）
     */
    private Boolean isAudit;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


}
