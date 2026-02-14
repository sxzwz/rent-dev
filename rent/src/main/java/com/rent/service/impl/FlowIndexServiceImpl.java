package com.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rent.context.LocalThreadHolder;
import com.rent.mapper.FlowIndexMapper;
import com.rent.mapper.HouseMapper;
import com.rent.mapper.HouseNewsMapper;
import com.rent.pojo.api.ApiResult;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.FlowIndexQueryDTO;
import com.rent.pojo.dto.HouseNewsQueryDTO;
import com.rent.pojo.dto.HouseQueryDTO;
import com.rent.pojo.emum.FlowIndexEnum;
import com.rent.pojo.entity.FlowIndex;
import com.rent.pojo.vo.HouseListItemVO;
import com.rent.pojo.vo.HouseNewsListVO;
import com.rent.service.FlowIndexService;
import com.rent.utils.AssertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 流量指标业务逻辑接口实现类
 */
@Service
public class FlowIndexServiceImpl extends ServiceImpl<FlowIndexMapper, FlowIndex> implements FlowIndexService {


    @Autowired
    HouseMapper houseMapper;
    @Autowired
    HouseNewsMapper houseNewsMapper;

    /**
     * 新增流量指标信息
     *
     * @param flowIndex
     * @return
     */
    @Override
    public Result<String> saveEntity(FlowIndex flowIndex) {
        AssertUtils.notNull(flowIndex, "实体数据不能为空");
        AssertUtils.notNull(flowIndex.getType(), "请指定流量类型");
        AssertUtils.hasText(flowIndex.getContentType(), "请指定内容模块");
        AssertUtils.notNull(flowIndex.getContentId(), "请指定内容ID");
        if (Objects.equals(flowIndex.getType(), FlowIndexEnum.REMAIN.getType())) {
            AssertUtils.notNull(flowIndex.getTimes(), "停留时长不能为空");
        }
        flowIndex.setUserId(LocalThreadHolder.getUserId());
        flowIndex.setCreateTime(LocalDateTime.now());
        save(flowIndex);
        return ApiResult.success();
    }

    /**
     * 查询流量指标信息
     *
     * @param flowIndexQueryDTO
     * @return
     */
    @Override
    public Result<List<FlowIndex>> list(FlowIndexQueryDTO flowIndexQueryDTO) {
        List<FlowIndex> flowIndexList = this.baseMapper.list(flowIndexQueryDTO);
        Integer count = this.baseMapper.listCount(flowIndexQueryDTO);
        return ApiResult.success(flowIndexList, count);
    }

    /**
     * 浏览操作
     *
     * @param flowIndex
     * @return
     */
    @Override
    public Result<String> viewOperation(FlowIndex flowIndex) {
        // 1.判断实体信息
        judge(flowIndex);
        FlowIndexQueryDTO flowIndexQueryDTO = createQueryDTO(
                FlowIndexEnum.VIEW.getType(),
                flowIndex.getContentId(),
                flowIndex.getContentType()
        );
        // 2.判断用户是不是已经浏览了这篇信息
        Integer count = this.baseMapper.listCount(flowIndexQueryDTO);
        // 如果已经浏览，直接抛出异常
        AssertUtils.isFalse(count != 0, "已产生浏览行为");
        FlowIndex entity = createEntity(
                FlowIndexEnum.VIEW.getType(),
                flowIndex.getContentId(),
                flowIndex.getContentType()
        );
        // 如果未浏览,保存浏览实体信息
        save(entity);
        return ApiResult.success();
    }

    /**
     * 收藏操作
     *
     * @param flowIndex
     * @return
     */
    @Override
    public Result<String> saveOperation(FlowIndex flowIndex) {
        // 1.判断实体信息
        judge(flowIndex);
        FlowIndexQueryDTO flowIndexQueryDTO = createQueryDTO(
                FlowIndexEnum.VIEW.getType(),
                flowIndex.getContentId(),
                flowIndex.getContentType()
        );
        // 2.判断用户是否收藏
        List<FlowIndex> flowIndexList = this.baseMapper.list(flowIndexQueryDTO);
        if (!flowIndexList.isEmpty()) {
            // 1.若不为空则取消收藏
            removeById(flowIndexList.get(0).getId());
            return ApiResult.success("取消收藏成功");
        }
        FlowIndex entity = createEntity(FlowIndexEnum.COLLECTION.getType(),
                flowIndex.getContentId(),
                flowIndex.getContentType());
        // 若为空则保存实体信息
        save(entity);
        return ApiResult.success("收藏成功");
    }

    /**
     * 停留操作
     *
     * @param flowIndex
     * @return
     */
    @Override
    public Result<String> stayOperation(FlowIndex flowIndex) {
        // 1.判断实体信息
        judge(flowIndex);
        AssertUtils.notNull(flowIndex.getTimes(), "时长不为空");
        FlowIndex entity = createEntity(
                FlowIndexEnum.REMAIN.getType(),
                flowIndex.getContentId(),
                flowIndex.getContentType()
        );
        entity.setTimes(flowIndex.getTimes());
        save(entity);
        return ApiResult.success();
    }

    /**
     * 查询房屋收藏的房屋数据
     *
     * @param flowIndexQueryDTO
     * @return
     */
    @Override
    public Result<List<HouseListItemVO>> saveListHouse(FlowIndexQueryDTO flowIndexQueryDTO) {
        flowIndexQueryDTO.setContentType("HOUSE_INFO");
        List<FlowIndex> flowIndexList = this.baseMapper.list(flowIndexQueryDTO);
        if (flowIndexList.isEmpty()) {
            return ApiResult.success(new ArrayList<>());
        }
        Integer count = this.baseMapper.listCount(flowIndexQueryDTO);
        List<Integer> houseIds = flowIndexList.stream()
                .map(FlowIndex::getContentId)
                .collect(Collectors.toList());

        HouseQueryDTO houseQueryDTO = new HouseQueryDTO();
        houseQueryDTO.setIds(houseIds);
        List<HouseListItemVO> houseListItemVOS = houseMapper.list(houseQueryDTO);
        return ApiResult.success(houseListItemVOS, count);
    }

    /**
     * 查询房屋收藏的房屋资讯数据
     *
     * @param flowIndexQueryDTO
     * @return
     */
    @Override
    public Result<List<HouseListItemVO>> saveListHouseNews(FlowIndexQueryDTO flowIndexQueryDTO) {
        flowIndexQueryDTO.setContentType("HOUSE_INFO");
        List<FlowIndex> flowIndexList = getFlowList(flowIndexQueryDTO);
        if (flowIndexList.isEmpty()) {
            return ApiResult.success(new ArrayList<>());
        }
        Integer count = this.baseMapper.listCount(flowIndexQueryDTO);
        List<Integer> houseIds = flowIndexList.stream()
                .map(FlowIndex::getContentId)
                .collect(Collectors.toList());
        HouseQueryDTO houseQueryDTO = new HouseQueryDTO();
        houseQueryDTO.setIds(houseIds);
        List<HouseListItemVO> houseListItemVOS = houseMapper.list(houseQueryDTO);
        return ApiResult.success(houseListItemVOS, count);
    }

    private List<FlowIndex> getFlowList(FlowIndexQueryDTO flowIndexQueryDTO) {
        flowIndexQueryDTO.setType(FlowIndexEnum.COLLECTION.getType()); // 设置为收藏类型
        flowIndexQueryDTO.setUserId(LocalThreadHolder.getUserId()); // 设置上操作者ID
        return this.baseMapper.list(flowIndexQueryDTO);
    }

    private FlowIndex createEntity(Integer type, Integer contentId, String contentType) {
        FlowIndex flowIndex = new FlowIndex();
        flowIndex.setType(type); // 声明这种行为操作是浏览操作
        flowIndex.setContentType(contentType);
        flowIndex.setContentId(contentId);
        flowIndex.setUserId(LocalThreadHolder.getUserId());
        flowIndex.setCreateTime(LocalDateTime.now());
        return flowIndex;
    }

    private void judge(FlowIndex flowIndex) {
        AssertUtils.notNull(flowIndex, "实体数据不能为空");
        AssertUtils.hasText(flowIndex.getContentType(), "请指定内容模块");
        AssertUtils.notNull(flowIndex.getContentId(), "请指定内容ID");
    }

    private FlowIndexQueryDTO createQueryDTO(Integer type, Integer contentId, String contentType) {
        FlowIndexQueryDTO flowIndexQueryDTO = new FlowIndexQueryDTO();
        flowIndexQueryDTO.setUserId(LocalThreadHolder.getUserId());
        flowIndexQueryDTO.setType(type);
        flowIndexQueryDTO.setContentId(contentId);
        flowIndexQueryDTO.setContentType(contentType);
        return flowIndexQueryDTO;
    }
}
