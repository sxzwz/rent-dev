package com.rent.pojo.vo;

import com.rent.pojo.dto.QueryDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 公告信息查询条件类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NoticeQueryDTO extends QueryDTO {
    /**
     * 标题
     */
    private String title;
}