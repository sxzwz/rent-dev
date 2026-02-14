package com.rent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.rent.pojo.entity.Notice;
import com.rent.pojo.vo.NoticeListItemVO;
import com.rent.pojo.vo.NoticeQueryDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 公告持久化接口
 */
@Mapper
public interface NoticeMapper extends BaseMapper<Notice> {

    List<NoticeListItemVO> list(NoticeQueryDTO noticeQueryDTO);

    Integer listCount(NoticeQueryDTO noticeQueryDTO);

}