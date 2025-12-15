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
 * 房屋预约信息
 * </p>
 *
 * @author syk
 * @since 2025-12-04
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("house_order_info")


public class HouseOrderInfo{



    /**
     * 房屋预约信息表主键ID，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户ID，外键，关联的是用户表
     */
    private Integer userId;

    /**
     * 房屋ID，外键，关联的是房屋信息表
     */
    private Integer houseId;

    /**
     * 预约日期
     */
    private String orderDate;

    /**
     * 预约时间段
     */
    private String orderTimeSplit;

    /**
     * 预约状态（1：预约中；2：已预约；3：预约失败；4：已取消；5：已完成）
     */
    private Integer orderStatus;

    /**
     * 拒绝原由（这个字段是为了预约失败服务的）
     */
    private String rejectCause;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;


}
