package com.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rent.mapper.AreaMapper;
import com.rent.pojo.api.ApiResult;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.AreaQueryDTO;
import com.rent.pojo.entity.Area;
import com.rent.service.AreaService;
import com.rent.utils.AssertUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements AreaService {

    /**
     * 新增地区信息
     * @param area
     * @return
     */
    @Override
    public Result<String> saveEntity(Area area) {
        // 1.校验地区信息是否合法
        parenDeal(area);
        // 2.保存新增地区信息
        save(area);
        return ApiResult.success("地区新增成功");
    }

    /**
     * 修改地区信息
     * @param area
     * @return
     */
    @Override
    public Result<String> update(Area area) {
        // 1.校验地区信息是否合法
        parenDeal(area);
        // 2.更新地区信息
        updateById(area);
        return ApiResult.success("地区修改成功");
    }

    /**
     * 查询地区信息
     *
     * @param areaQueryDTO
     * @return
     */
    @Override
    public Result<List<Area>> list(AreaQueryDTO areaQueryDTO) {
        List<Area> list = this.baseMapper.list(areaQueryDTO);
        Integer count = this.baseMapper.listCount(areaQueryDTO);
        return ApiResult.success(list, count);
    }


    private void parenDeal(Area area) {
        // 1.判断地区名是否为空
        AssertUtils.hasText(area.getName(), "地区名不能为空");
        // 2.判断该地区是否已存在
        AreaQueryDTO areaQueryDTO = new AreaQueryDTO();
        areaQueryDTO.setName(area.getName());
        Integer count = this.baseMapper.listCount(areaQueryDTO);
        AssertUtils.isTrue(count == 0, "该地区已存在");
    }
}
