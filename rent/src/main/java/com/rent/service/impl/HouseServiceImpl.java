package com.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rent.context.LocalThreadHolder;
import com.rent.mapper.FlowIndexMapper;
import com.rent.mapper.HouseMapper;
import com.rent.mapper.LandlordMapper;
import com.rent.pojo.api.ApiResult;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.FlowIndexQueryDTO;
import com.rent.pojo.dto.HouseQueryDTO;
import com.rent.pojo.dto.LandlordQueryDTO;
import com.rent.pojo.dto.QueryDTO;
import com.rent.pojo.emum.*;
import com.rent.pojo.entity.FlowIndex;
import com.rent.pojo.entity.House;
import com.rent.pojo.vo.*;
import com.rent.service.FlowIndexService;
import com.rent.service.HouseService;
import com.rent.utils.AssertUtils;
import com.rent.utils.DateUtil;
import com.rent.utils.UserBasedCFUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 房屋信息业务逻辑接口实现类
 */
@Service
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements HouseService {

    @Autowired
    private LandlordMapper landlordMapper;
    @Autowired
    private FlowIndexService flowIndexService;
    @Autowired
    private FlowIndexMapper flowIndexMapper;

    /**
     * 新增房屋信息
     *
     * @param house
     * @return
     */
    @Override
    public Result<String> saveEntity(House house) {
        // 校验房屋信息
        paramJudge(house);
        // 判断用户角色(只有房东才能添加房屋信息)
        LandlordQueryDTO landlordQueryDTO = new LandlordQueryDTO();
        landlordQueryDTO.setUserId(LocalThreadHolder.getUserId());
        List<LandlordVO> landlordVOS = landlordMapper.list(landlordQueryDTO);
        AssertUtils.isFalse(landlordVOS.isEmpty(), "房东信息异常,非法操作");
        LandlordVO landlordVO = landlordVOS.get(0);
        AssertUtils.isTrue(landlordVO.getIsAudit(), "房东认证信息待审核中，请稍后再试");
        house.setLandlordId(landlordVO.getId());
        house.setCreateTime(LocalDateTime.now());
        save(house);

        return ApiResult.success("房屋信息添加成功");
    }

    /**
     * 修改房屋信息
     *
     * @param house
     * @return
     */
    @Override
    public Result<String> update(House house) {
        // 校验房屋信息
        paramJudge(house);
        // 修改房屋信息
        updateById(house);
        return ApiResult.success("房屋信息修改成功");
    }

    /**
     * 查询房屋信息
     *
     * @param houseQueryDTO
     * @return
     */
    @Override
    public Result<List<HouseListItemVO>> list(HouseQueryDTO houseQueryDTO) {
        List<HouseListItemVO> houseListItemVOS = this.baseMapper.list(houseQueryDTO);
        Integer count = this.baseMapper.listCount(houseQueryDTO);
        for (HouseListItemVO houseListItemVO : houseListItemVOS) {
            // 通过朝向ID,设置房屋朝向信息
            if (Objects.nonNull(houseListItemVO.getDirectionId())) {
                String detail = HouseDirectionEnum.getDetail(houseListItemVO.getDirectionId());
                houseListItemVO.setDirectionName(detail);
            }
            // 通过押金方式ID,设置房屋押金信息
            if (Objects.nonNull(houseListItemVO.getDepositMethodId())) {
                String detail = HouseDepositEnum.getDetail(houseListItemVO.getDepositMethodId());
                houseListItemVO.setDepositMethodName(detail);
            }
            // 通过装修状态ID,设置房屋装修状态信息
            if (Objects.nonNull(houseListItemVO.getFitmentStatusId())) {
                String detail = HouseFitmentEnum.getDetail(houseListItemVO.getFitmentStatusId());
                houseListItemVO.setFitmentStatusName(detail);
            }

        }
        return ApiResult.success(houseListItemVOS, count);
    }

    /**
     * 查询房屋类型列表
     *
     * @return
     */
    @Override
    public Result<List<SelectedVO>> houseTypeList() {
        List<SelectedVO> selectedVOS = Arrays.stream(HouseTypeEnum.values())
                .map(houseTypeEnum -> new SelectedVO(houseTypeEnum.getType(), houseTypeEnum.getDetail()))
                .collect(Collectors.toList());
        return ApiResult.success(selectedVOS);
    }

    /**
     * 查询房屋朝向列表
     *
     * @return
     */
    @Override
    public Result<List<SelectedVO>> houseDirectionList() {
        List<SelectedVO> selectedVOS = Arrays.stream(HouseDirectionEnum.values())
                .map(houseDirectionEnum -> new SelectedVO(houseDirectionEnum.getType(), houseDirectionEnum.getDetail()))
                .collect(Collectors.toList());
        return ApiResult.success(selectedVOS);
    }

    /**
     * 查询房屋大小列表
     *
     * @return
     */
    @Override
    public Result<List<SelectedVO>> houseSizedList() {
        List<SelectedVO> selectedVOS = Arrays.stream(HouseSizedEnum.values())
                .map(houseSizedEnum -> new SelectedVO(houseSizedEnum.getType(), houseSizedEnum.getDetail()))
                .collect(Collectors.toList());
        return ApiResult.success(selectedVOS);
    }

    /**
     * 查询房屋租金方式列表
     *
     * @return
     */
    @Override
    public Result<List<SelectedVO>> houseDepositMethodList() {
        List<SelectedVO> selectedVOS = Arrays.stream(HouseDepositEnum.values())
                .map(houseDepositMethodEnum -> new SelectedVO(houseDepositMethodEnum.getType(), houseDepositMethodEnum.getDetail()))
                .collect(Collectors.toList());
        return ApiResult.success(selectedVOS);
    }

    /**
     * 查询房屋是否临近地铁列表
     *
     * @return
     */
    @Override
    public Result<List<SelectedVO>> houseSubwayList() {
        List<SelectedVO> selectedVOS = Arrays.stream(IsSubwayEnum.values())
                .map(isSubwayEnum -> new SelectedVO(isSubwayEnum.getType(), isSubwayEnum.getDetail()))
                .collect(Collectors.toList());
        return ApiResult.success(selectedVOS);
    }

    /**
     * 查询房屋装修状态
     *
     * @return
     */
    @Override
    public Result<List<SelectedVO>> houseFitmentStatusList() {
        List<SelectedVO> selectedVOS = Arrays.stream(HouseFitmentEnum.values())
                .map(houseFitmentEnum -> new SelectedVO(houseFitmentEnum.getType(), houseFitmentEnum.getDetail()))
                .collect(Collectors.toList());
        return ApiResult.success(selectedVOS);
    }

    /**
     * 查询房屋租赁方式
     *
     * @return
     */
    @Override
    public Result<List<SelectedVO>> houseRentalTypeList() {
        List<SelectedVO> selectedVOS = Arrays.stream(RentalTypeEnum.values())
                .map(rentalTypeEnum -> new SelectedVO(rentalTypeEnum.getType(), rentalTypeEnum.getDetail()))
                .collect(Collectors.toList());
        return ApiResult.success(selectedVOS);
    }

    /**
     * 查询房屋设施列表
     *
     * @return
     */
    @Override
    public Result<List<LivingFacilityVO>> houseLivingFacilityList() {
        List<LivingFacilityVO> livingFacilityVOS = Arrays.stream(LivingFacilitiesEnum.values())
                .map(livingFacilitiesEnum -> new LivingFacilityVO(livingFacilitiesEnum.getType(), livingFacilitiesEnum.getSelected()))
                .collect(Collectors.toList());
        return ApiResult.success(livingFacilityVOS);
    }


    /**
     * 查询房东自己的房源信息
     *
     * @param houseQueryDTO
     * @return
     */
    @Override
    public Result<List<HouseListItemVO>> landlordHouseList(HouseQueryDTO houseQueryDTO) {
        LandlordVO landlordVO = getLandlord();
        houseQueryDTO.setLandlordId(landlordVO.getId());
        List<HouseListItemVO> houseListItemVOS = this.baseMapper.list(houseQueryDTO);
        dealHouseStatus(houseListItemVOS);
        Integer count = this.baseMapper.listCount(houseQueryDTO);
        return ApiResult.success(houseListItemVOS, count);
    }

    /**
     * 房东上架/下架房源信息
     *
     * @param id
     * @return
     */
    @Override
    public Result<String> houseStatusDeal(Integer id) {
        AssertUtils.notNull(id, "房源ID不能为空");
        // 先通过ID查询房源信息
        House house = getById(id);
        AssertUtils.notNull(house, "房源信息异常");
        AssertUtils.notNull(house.getLandlordId(), "房源信息异常");
        boolean equals = Objects.equals(house.getStatus(), HouseStatusEnum.STATUS_1.getType());
        house.setStatus(equals
                ? HouseStatusEnum.STATUS_2.getType()
                : HouseStatusEnum.STATUS_1.getType());
        if (Objects.equals(LocalThreadHolder.getRoleId(), RoleEnum.ADMIN.getRole())) {
            updateById(house);
            return ApiResult.success(equals ? "房屋已下架" : "房屋已上架");
        }
        Integer landlordId = house.getLandlordId(); // 取得房东ID
        LandlordVO landlord = getLandlord(); // 当前操作者认证的房东信息
        AssertUtils.notNull(landlord, "房东信息异常");
        AssertUtils.isTrue(landlord.getIsAudit(), "房东信息未审核");
        AssertUtils.equals(landlordId, landlord.getId(), "非法操作");

        updateById(house);
        return ApiResult.success(equals ? "房屋已下架" : "房屋已上架");
    }

    /**
     * 通过ID查询房屋详情信息
     *
     * @param id
     * @return
     */
    @Override
    public Result<HouseVO> selectById(Integer id) {
        HouseVO houseVO = this.baseMapper.getById(id);
        List<HouseVO> houseVOList = new ArrayList<>();
        houseVOList.add(houseVO);
        dealHouseVOStatus(houseVOList);
        return ApiResult.success(houseVO);
    }

    /**
     * 查询房屋面积条件查询条件范围
     *
     * @return
     */
    @Override
    public Result<List<SelectedVO>> houseSizeNumber() {
        List<SelectedVO> selectedVOS = Arrays.stream(HouseSizeNumberEnum.values())
                .map(houseSizeNumberEnum -> new SelectedVO(houseSizeNumberEnum.getType(), houseSizeNumberEnum.getDetail()))
                .collect(Collectors.toList());
        return ApiResult.success(selectedVOS);
    }

    /**
     * 查询房屋租金查询条件范围
     *
     * @return
     */
    @Override
    public Result<List<SelectedVO>> houseRentRange() {
        List<SelectedVO> selectedVOS = Arrays.stream(HouseRentEnum.values())
                .map(houseRentEnum -> new SelectedVO(houseRentEnum.getType(), houseRentEnum.getDetail()))
                .collect(Collectors.toList());
        return ApiResult.success(selectedVOS);
    }

    /**
     * 统计房屋流量
     *
     * @param houseQueryDTO
     * @return
     */
    @Override
    public Result<List<HouseFlowIndexVO>> listFlowIndex(HouseQueryDTO houseQueryDTO) {
        // 前提是房东来查的
        // 1. 通过用户ID去查找用户自己已经认证的房东信息，取出他的房东ID
        LandlordVO landlord = getLandlord();
        AssertUtils.isTrue(landlord.getIsAudit(), "房东认证信息待审核，请稍后再试");
        // 2. 凭着查出来的房东ID，去房屋信息表查询该房东自己名下维护的所有房屋ID，取列表
        List<Integer> houseIds = this.baseMapper.selectIdsByLandlordId(landlord.getId());
        if (houseIds.isEmpty()) {
            return ApiResult.success(new ArrayList<>());
        }
        HouseQueryDTO queryDto = new HouseQueryDTO();
        queryDto.setIds(houseIds);
        List<HouseFlowIndexVO> houseFlowIndexVOS = this.baseMapper.flowIndexList(queryDto);
        dealClickRate(houseFlowIndexVOS);
        Integer count = this.baseMapper.listCount(queryDto);
        return ApiResult.success(houseFlowIndexVOS, count);
    }

    /**
     * 流量指标可视化
     *
     * @param houseQueryDTO 查询参数
     * @return Result<List < ChartVO>> 响应结果
     */
    @Override
    public Result<List<ChartVO>> listChart(HouseQueryDTO houseQueryDTO) {
        flowIndexListParamJudge(houseQueryDTO);
        FlowIndexQueryDTO flowIndexQueryDto = createFlowIndexQueryDTO(houseQueryDTO);
        List<FlowIndex> flowIndexList = flowIndexMapper.list(flowIndexQueryDto);
        if (flowIndexList == null || flowIndexList.isEmpty()) {
            return ApiResult.success(new ArrayList<>());
        }
        // 只取出里面的创建时间，形成一个新的数组
        List<LocalDateTime> dateTimeList = flowIndexList.stream()
                .map(FlowIndex::getCreateTime)
                .collect(Collectors.toList());
        List<ChartVO> chartVOS = DateUtil.countDatesWithinRange(houseQueryDTO.getDays(), dateTimeList);
        return ApiResult.success(chartVOS);
    }


    /**
     * 房屋推荐
     * @param count
     * @return
     */
    @Override
    public Result<List<HouseListItemVO>> recommend(Integer count) {
        AssertUtils.notNull(count, "推荐条数不能为空");
        FlowIndexQueryDTO flowIndexQueryDTO = new FlowIndexQueryDTO();
        flowIndexQueryDTO.setContentType("HOUSE_INFO"); // 做的是房屋的推荐，所以模块设置为房屋模块
        // 获取所有的房屋ID列表
        List<Integer> houseIds = this.baseMapper.getIds();
        if (houseIds.isEmpty()) {
            return ApiResult.success(new ArrayList<>());
        }
        // 获取用户对于房屋资讯的评分
        List<ScoreVO> scores = flowIndexMapper.getScores(flowIndexQueryDTO);
        if (scores.isEmpty()) {
            List<HouseListItemVO> houseListItemVOS = queryHouseNews(houseIds.size() > count ? houseIds.subList(0, count) : houseIds);
            return ApiResult.success(houseListItemVOS);
        }
        // 构建用户对于物品评分的指定数据结构
        List<UserBasedCFUtil.Score> scoreList = scores.stream().map(score -> new UserBasedCFUtil.Score(
                score.getUserId(),
                score.getContentId(),
                score.getScore()
        )).collect(Collectors.toList());
        // 构建用户对于房屋的评分矩阵
        Map<Integer, Map<Integer, Double>> userItemMatrix =
                UserBasedCFUtil.buildUserItemMatrix(houseIds, scoreList);
        // 创建协同过滤工具实例
        UserBasedCFUtil cfUtil = new UserBasedCFUtil(userItemMatrix);
        // 算出向指定这个用户推荐的房屋资讯
        List<Integer> itemIds = cfUtil.recommendItems(LocalThreadHolder.getUserId(), count);
        // 冷启动：用户是新用户，没有一丁点儿的互动数据，何谈兴趣？何谈推荐？
        if (itemIds.isEmpty()) {
            // 如果最初查出来的房屋的ID列表，比咱们接口需要的更多，则截取，反之直接用
            List<HouseListItemVO> houseListItemVOS = queryHouseNews(houseIds.size() > count ? houseIds.subList(0, count) : houseIds);
            return ApiResult.success(houseListItemVOS);
        }
        List<HouseListItemVO> houseListItemVOS = queryHouseNews(itemIds);
        return ApiResult.success(houseListItemVOS);
    }

    /**
     * 通过房屋资讯的ID列表查询房屋资讯
     *
     * @param ids 房屋资讯ID列表
     * @return List<HouseNewsListVO>
     */
    private List<HouseListItemVO> queryHouseNews(List<Integer> ids) {
        HouseQueryDTO houseQueryDTO = new HouseQueryDTO();
        houseQueryDTO.setIds(ids);
        return this.baseMapper.list(houseQueryDTO);
    }

    private void flowIndexListParamJudge(HouseQueryDTO houseQueryDTO) {
        AssertUtils.notNull(houseQueryDTO, "查询条件不能为空");
        AssertUtils.notNull(houseQueryDTO.getId(), "内容ID不能为空");
        AssertUtils.notNull(houseQueryDTO.getType(), "流量类型不能为空");
        AssertUtils.notNull(houseQueryDTO.getDays(), "查询天数不能为空");
    }

    private FlowIndexQueryDTO createFlowIndexQueryDTO(HouseQueryDTO houseQueryDTO) {
        FlowIndexQueryDTO flowIndexQueryDTO = new FlowIndexQueryDTO();
        flowIndexQueryDTO.setContentType("HOUSE_INFO");
        flowIndexQueryDTO.setContentId(houseQueryDTO.getId());
        flowIndexQueryDTO.setType(houseQueryDTO.getType());
        QueryDTO queryDTO = DateUtil.startAndEndTime(houseQueryDTO.getDays());
        flowIndexQueryDTO.setStartTime(queryDTO.getStartTime());
        flowIndexQueryDTO.setEndTime(queryDTO.getEndTime());
        return flowIndexQueryDTO;
    }


    /**
     * 校验房屋信息
     *
     * @param house
     */
    private void paramJudge(House house) {
        AssertUtils.hasText(house.getName(), "房屋名称不能为空");
        AssertUtils.hasText(house.getCover(), "房屋封面不能为空");
        AssertUtils.hasText(house.getFloor(), "请填写楼层");
        AssertUtils.hasText(house.getCovers(), "房屋实况图不能为空");
        AssertUtils.notNull(house.getTypeId(), "请设置房屋类型");
        AssertUtils.notNull(house.getSizeNumber(), "请填写房屋产权面积");
        AssertUtils.notNull(house.getDirectionId(), "请设置房屋朝向");
        AssertUtils.notNull(house.getSizedId(), "请设置户型");
        AssertUtils.notNull(house.getRent(), "请设置租金");
        AssertUtils.notNull(house.getDepositMethodId(), "请设置押金方式");
        AssertUtils.notNull(house.getIsSubway(), "请设置是否临近地铁");
        if (house.getIsSubway()) { // 一旦你设置临近地铁，就要补充线路
            AssertUtils.notNull(house.getSubwayLine(), "请设置地铁线路");
        }
        AssertUtils.notNull(house.getFitmentStatusId(), "请设置装修类型");
        AssertUtils.notNull(house.getRentalType(), "请设置租赁方式");
        // 名称长度显示
        AssertUtils.isTrue(house.getName().length() < 30, "房屋名称最多30个字"); // 名称长度校验
        // 实况图上传数量显示
        String covers = house.getCovers();
        long count = covers.chars().filter(c -> c == ',').count();
        AssertUtils.isTrue(count < 6, "实况图最多上传6张");
        // 楼层只能设置为高中低
        AssertUtils.isTrue(house.getFloor().length() <= 1, "楼层只能补充为高或者低、中");
    }

    private LandlordVO getLandlord() {
        LandlordQueryDTO landlordQueryDTO = new LandlordQueryDTO();
        landlordQueryDTO.setUserId(LocalThreadHolder.getUserId());
        List<LandlordVO> landlordVOS = landlordMapper.list(landlordQueryDTO);
        AssertUtils.isFalse(landlordVOS.isEmpty(), "房东信息异常,非法操作");
        return landlordVOS.get(0);
    }

    private void dealHouseStatus(List<HouseListItemVO> houseListItemVOS) {
        for (HouseListItemVO houseListItemVO : houseListItemVOS) {
            // 通过朝向ID，设置朝向文本字样
            if (Objects.nonNull(houseListItemVO.getDirectionId())) {
                String detail = HouseDirectionEnum.getDetail(houseListItemVO.getDirectionId());
                houseListItemVO.setDirectionName(detail);
            }
            // 通过押金方式ID，设置押金方式文本字样
            if (Objects.nonNull(houseListItemVO.getDepositMethodId())) {
                String detail = HouseDepositEnum.getDetail(houseListItemVO.getDepositMethodId());
                houseListItemVO.setDepositMethodName(detail);
            }
            // 通过装修状态ID，设置装修状态文本字样
            if (Objects.nonNull(houseListItemVO.getFitmentStatusId())) {
                String detail = HouseFitmentEnum.getDetail(houseListItemVO.getFitmentStatusId());
                houseListItemVO.setFitmentStatusName(detail);
            }
        }
    }

    private void dealHouseVOStatus(List<HouseVO> houseVOS) {
        for (HouseVO houseVO : houseVOS) {
            // 通过朝向ID，设置朝向文本字样
            if (Objects.nonNull(houseVO.getDirectionId())) {
                String detail = HouseDirectionEnum.getDetail(houseVO.getDirectionId());
                houseVO.setDirectionName(detail);
            }
            // 通过押金方式ID，设置押金方式文本字样
            if (Objects.nonNull(houseVO.getDepositMethodId())) {
                String detail = HouseDepositEnum.getDetail(houseVO.getDepositMethodId());
                houseVO.setDepositMethodName(detail);
            }
            // 通过装修状态ID，设置装修状态文本字样
            if (Objects.nonNull(houseVO.getFitmentStatusId())) {
                String detail = HouseFitmentEnum.getDetail(houseVO.getFitmentStatusId());
                houseVO.setFitmentStatusName(detail);
            }
            // 通过房屋类型ID，设置房屋类型文本字样
            if (Objects.nonNull(houseVO.getTypeId())) {
                String detail = HouseTypeEnum.getDetail(houseVO.getTypeId());
                houseVO.setTypeName(detail);
            }
            // 通过户型类型ID，设置户型文本字样
            if (Objects.nonNull(houseVO.getSizedId())) {
                String detail = HouseSizedEnum.getDetail(houseVO.getTypeId());
                houseVO.setSizedName(detail);
            }
            // 设置租赁方式
            if (Objects.nonNull(houseVO.getRentalType())) {
                String detail = RentalTypeEnum.getDetail(houseVO.getRentalType());
                houseVO.setRentalTypeName(detail);
            }
        }
    }

    /**
     * 计算并设置点击率（点击率 = 阅读量 / 展现量 * 100%）
     *
     * @param houseFlowIndexVOS 流量数据列表
     */
    private void dealClickRate(List<HouseFlowIndexVO> houseFlowIndexVOS) {
        if (houseFlowIndexVOS == null || houseFlowIndexVOS.isEmpty()) {
            return;
        }

        for (HouseFlowIndexVO vo : houseFlowIndexVOS) {
            // 确保展现量和阅读量都不为null且展现量不为0
            if (vo.getShowNumber() != null && vo.getViewNumber() != null
                    && vo.getShowNumber() != 0) {
                // 计算点击率（百分比形式，保留2位小数）
                double rate = (double) vo.getViewNumber() / vo.getShowNumber() * 100;
                vo.setClickRate(Double.parseDouble(String.format("%.2f", rate)));
            } else {
                // 如果数据不完整，点击率设为0
                vo.setClickRate(0.0);
            }
        }
    }

}
