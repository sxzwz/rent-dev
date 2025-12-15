package com.rent.controller;

import com.rent.aop.Pager;
import com.rent.pojo.api.ApiResult;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.LandlordQueryDTO;
import com.rent.pojo.entity.Landlord;
import com.rent.pojo.vo.LandlordVO;
import com.rent.service.LandlordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 房东控制器
 */

@RestController
@RequestMapping("/landLord")
@Tag(name = "房东信息长居驻地管理")
public class LandlordController {

    @Autowired
    private LandlordService landlordService;

    /**
     * 新增房东信息
     *
     * @param landlord
     * @return
     */
    @PostMapping("/save")
    public Result<String> save(@RequestBody Landlord landlord) {
        return landlordService.saveEntity(landlord);
    }

    /**
     * 删除房东信息
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        landlordService.removeById(id);
        return ApiResult.success("房东删除成功");

    }

    @PutMapping("/update")
    public Result<String> update(@RequestBody Landlord landlord){
        return landlordService.update(landlord);
    }

    @Pager
    @PostMapping("/listUser")
    public Result<LandlordVO> listUser(@RequestBody LandlordQueryDTO landlordQueryDTO){
        return landlordService.listUser(landlordQueryDTO);
    }

    /**
     * 查询房东信息
     *
     * @param landlordQueryDTO 查询参数
     * @return Result<List < LandlordVO>> 响应结果
     */
    @Pager
    @PostMapping( "/list")
    public Result<List<LandlordVO>> list(@RequestBody LandlordQueryDTO landlordQueryDTO) {
        return landlordService.list(landlordQueryDTO);
    }

}
