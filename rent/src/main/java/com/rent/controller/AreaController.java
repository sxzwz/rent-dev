package com.rent.controller;


import com.rent.aop.Pager;
import com.rent.pojo.api.ApiResult;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.AreaQueryDTO;
import com.rent.pojo.entity.Area;
import com.rent.pojo.entity.UserArea;
import com.rent.service.AreaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 地区控制器
 */
@RestController
@RequestMapping("/area")
@Tag(name = "地区管理")
public class AreaController {

    @Autowired
    private AreaService areaService;

    /**
     * 新增地区信息
     * @param area
     * @return
     */
    @PostMapping("/save")
    public Result<String> save(@RequestBody Area area) {
        return areaService.saveEntity(area);
    }

    /**
     * 删除地区信息
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        areaService.removeById(id);
        return ApiResult.success("地区删除成功");
    }

    /**
     * 修改地区信息
     * @param area
     * @return
     */
    @PutMapping("/update")
    public Result<String> update(@RequestBody Area area){
        return areaService.update(area);
    }

    @Pager
    @PostMapping("/list")
    public Result<List<Area>> list(@RequestBody AreaQueryDTO areaQueryDTO){
        return areaService.list(areaQueryDTO);
    }
}
