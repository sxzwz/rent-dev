package com.rent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.HouseQueryDTO;
import com.rent.pojo.entity.House;
import com.rent.pojo.vo.ChartVO;
import com.rent.pojo.vo.HouseFlowIndexVO;
import com.rent.pojo.vo.HouseListItemVO;
import com.rent.pojo.vo.HouseVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.Base64;
import java.util.List;

@Mapper
public interface HouseMapper extends BaseMapper<House> {


    List<HouseListItemVO> list(HouseQueryDTO houseQueryDto);

    Integer listCount(HouseQueryDTO houseQueryDto);

    HouseVO getById(Integer id);

    List<Integer> selectIdsByLandlordId(Integer landlordId);

    List<HouseFlowIndexVO> flowIndexList(HouseQueryDTO houseQueryDTO);

    List<HouseListItemVO> listCreate(HouseQueryDTO houseQueryDTO);

    List<ChartVO> cityHouseRange(Integer limit);

    List<Integer> getIds();
}
