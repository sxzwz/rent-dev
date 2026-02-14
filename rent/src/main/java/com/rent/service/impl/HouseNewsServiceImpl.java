package com.rent.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rent.context.LocalThreadHolder;
import com.rent.mapper.FlowIndexMapper;
import com.rent.mapper.HouseNewsMapper;
import com.rent.pojo.api.ApiResult;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.FlowIndexQueryDTO;
import com.rent.pojo.dto.HouseNewsQueryDTO;
import com.rent.pojo.entity.HouseNews;
import com.rent.pojo.vo.HouseNewsListVO;
import com.rent.pojo.vo.ScoreVO;
import com.rent.service.HouseNewsService;
import com.rent.utils.AssertUtils;
import com.rent.utils.UserBasedCFUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 房屋资讯业务逻辑接口实现类
 */
@Service
public class HouseNewsServiceImpl extends ServiceImpl<HouseNewsMapper, HouseNews> implements HouseNewsService {

    @Autowired
    private FlowIndexMapper flowIndexMapper;

    /**
     * 新增房屋资讯信息
     * @param houseNews
     * @return
     */
    @Override
    public Result<String> saveEntity(HouseNews houseNews) {
        judge(houseNews);
        houseNews.setCreateTime(LocalDateTime.now());
        save(houseNews);
        return ApiResult.success();
    }

    /**
     * 修改房屋资讯信息
     * @param houseNews
     * @return
     */
    @Override
    public Result<String> updateEntity(HouseNews houseNews) {
        judge(houseNews);
        updateById(houseNews);
        return ApiResult.success();
    }

    /**
     * 通过ID查询房屋咨询
     * @param id
     * @return
     */
    @Override
    public Result<HouseNews> selectById(Integer id) {
        AssertUtils.notNull(id, "ID不能为空");
        HouseNews houseNews = getById(id);
        return ApiResult.success(houseNews);
    }

    /**
     * 查询房屋资讯信息
     * @param houseNewsQueryDTO
     * @return
     */
    @Override
    public Result<List<HouseNewsListVO>> list(HouseNewsQueryDTO houseNewsQueryDTO) {
        List<HouseNewsListVO> houseNewsListVOList = this.baseMapper.list(houseNewsQueryDTO);
        Integer count = this.baseMapper.listCount(houseNewsQueryDTO);
        return ApiResult.success(houseNewsListVOList, count);
    }

    /**
     * 房屋资讯推荐
     * @param count
     * @return
     */
    @Override
    public Result<List<HouseNewsListVO>> recommend(Integer count) {
        AssertUtils.notNull(count, "推荐条数不能为空");
        FlowIndexQueryDTO flowIndexQueryDTO = new FlowIndexQueryDTO();
        flowIndexQueryDTO.setContentType("HOUSE_NEWS"); // 做的是房屋资讯的推荐，所以模块设置为房屋资讯模块
        // 获取所有的房屋资讯ID列表
        List<Integer> houseNewsIds = this.baseMapper.getIds();
        if (houseNewsIds.isEmpty()) {
            return ApiResult.success(new ArrayList<>());
        }
        // 获取用户对于房屋资讯的评分
        List<ScoreVO> scores = flowIndexMapper.getScores(flowIndexQueryDTO);
        if (scores.isEmpty()) {
            List<HouseNewsListVO> houseNewsListVOList = queryHouseNews(houseNewsIds.size() > count ? houseNewsIds.subList(0, count) : houseNewsIds);
            return ApiResult.success(houseNewsListVOList);
        }
        // 构建用户对于物品评分的指定数据结构
        List<UserBasedCFUtil.Score> scoreList = scores.stream().map(score -> new UserBasedCFUtil.Score(
                score.getUserId(),
                score.getContentId(),
                score.getScore()
        )).collect(Collectors.toList());
        // 构建用户对于房屋资讯的评分矩阵
        Map<Integer, Map<Integer, Double>> userItemMatrix =
                UserBasedCFUtil.buildUserItemMatrix(houseNewsIds, scoreList);
        // 创建协同过滤工具实例
        UserBasedCFUtil cfUtil = new UserBasedCFUtil(userItemMatrix);
        // 算出向指定这个用户推荐的房屋资讯
        List<Integer> itemIds = cfUtil.recommendItems(LocalThreadHolder.getUserId(), count);
        // 冷启动：用户是新用户，没有一丁点儿的互动数据，何谈兴趣？何谈推荐？
        if (itemIds.isEmpty()) {
            // 如果最初查出来的房屋资讯的ID列表，比咱们接口需要的更多，则截取，反之直接用
            List<HouseNewsListVO> houseNewsListVOList = queryHouseNews(houseNewsIds.size() > count ? houseNewsIds.subList(0, count) : houseNewsIds);
            return ApiResult.success(houseNewsListVOList);
        }
        List<HouseNewsListVO> houseNewsListVOList = queryHouseNews(itemIds);
        return ApiResult.success(houseNewsListVOList);
    }

    /**
     * 通过房屋资讯的ID列表查询房屋资讯
     *
     * @param ids 房屋资讯ID列表
     * @return List<HouseNewsListVO>
     */
    private List<HouseNewsListVO> queryHouseNews(List<Integer> ids) {
        HouseNewsQueryDTO houseNewsQueryDTO = new HouseNewsQueryDTO();
        houseNewsQueryDTO.setIds(ids);
        return this.baseMapper.list(houseNewsQueryDTO);
    }

    private void judge(HouseNews houseNews) {
        AssertUtils.notNull(houseNews, "实体数据不能为空");
        AssertUtils.hasText(houseNews.getTitle(), "标题不能为空");
        AssertUtils.hasText(houseNews.getCover(), "封面不能为空");
        AssertUtils.hasText(houseNews.getSummary(), "摘要不能为空");
    }
}
