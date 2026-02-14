package com.rent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rent.pojo.api.Result;
import com.rent.pojo.entity.Notice;
import com.rent.pojo.vo.NoticeListItemVO;
import com.rent.pojo.vo.NoticeQueryDTO;


import java.util.List;

/**
 * 公告业务逻辑接口
 */
public interface NoticeService extends IService<Notice> {

    Result<List<NoticeListItemVO>> list(NoticeQueryDTO noticeQueryDTO);

    Result<String> saveEntity(Notice notice);

    Result<Notice> selectById(Integer id);

    Result<String> updateEntity(Notice notice);

}