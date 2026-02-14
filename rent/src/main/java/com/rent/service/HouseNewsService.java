package com.rent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.HouseNewsQueryDTO;
import com.rent.pojo.entity.HouseNews;
import com.rent.pojo.vo.HouseNewsListVO;

import java.util.List;

/**
 * 房屋资讯业务逻辑接口
 */
public interface HouseNewsService  extends IService<HouseNews> {

    // 新增房屋资讯信息
    Result<String> saveEntity(HouseNews houseNews);

    // 修改房屋资讯信息
    Result<String> updateEntity(HouseNews houseNews);

    // 通过ID查询
    Result<HouseNews> selectById(Integer id);

    // 查询房屋资讯信息
    Result<List<HouseNewsListVO>> list(HouseNewsQueryDTO houseNewsQueryDTO);

    //房屋资讯推荐
    Result<List<HouseNewsListVO>> recommend(Integer count);
}
