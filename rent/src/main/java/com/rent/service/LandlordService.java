package com.rent.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.LandlordQueryDTO;
import com.rent.pojo.entity.Landlord;
import com.rent.pojo.vo.LandlordVO;

import java.util.List;

/**
 * 房东信息业务逻辑接口
 */
public interface LandlordService extends IService<Landlord> {

     // 新增房东信息
     Result<String> saveEntity(Landlord landlord);

     // 修改房东信息
     Result<String> update(Landlord landlord);

     // 查询当前用户的房东信息
     Result<LandlordVO> listUser(LandlordQueryDTO landlordQueryDTO);

     // 查询房东信息
     Result<List<LandlordVO>> list(LandlordQueryDTO landlordQueryDTO);

}
