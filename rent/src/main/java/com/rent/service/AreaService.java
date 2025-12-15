package com.rent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.AreaQueryDTO;
import com.rent.pojo.entity.Area;
import org.springframework.stereotype.Service;

import java.util.List;

public interface AreaService extends IService<Area> {

    // 新增地区信息
    Result<String> saveEntity(Area area);

    // 修改地区信息
    Result<String> update(Area area);

    // 查询地区信息
    Result<List<Area>> list(AreaQueryDTO areaQueryDTO);


}
