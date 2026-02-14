package com.rent.service.impl;

import com.rent.context.LocalThreadHolder;
import com.rent.mapper.EvaluationsMapper;
import com.rent.mapper.EvaluationsUpvoteMapper;
import com.rent.mapper.UserMapper;
import com.rent.pojo.api.ApiResult;
import com.rent.pojo.api.Result;
import com.rent.pojo.dto.EvaluationsQueryDTO;
import com.rent.pojo.dto.EvaluationsQueryDTO;
import com.rent.pojo.entity.Evaluations;
import com.rent.pojo.entity.EvaluationsUpvote;
import com.rent.pojo.entity.User;
import com.rent.pojo.vo.CommentChildVO;
import com.rent.pojo.vo.CommentParentVO;
import com.rent.pojo.vo.EvaluationsVO;
import com.rent.service.EvaluationsService;
import com.rent.utils.AhoCorasickFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评论服务实现类
 */
@Service
public class EvaluationsServiceImpl implements EvaluationsService {

    @Autowired
    private EvaluationsMapper evaluationsMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EvaluationsUpvoteMapper evaluationsUpvoteMapper;

    /**
     * 评论
     *
     * @return Result<String>
     */
    @Override
    public Result<Object> insert(Evaluations evaluations) {
        // 原始评论文本
        String content = evaluations.getContent();
        // ====================Trie树敏感词过滤算法起始===============
        AhoCorasickFilter ahoCorasickFilter = new AhoCorasickFilter(); // 创建过滤器实例
        for (String sensitiveWord : AhoCorasickFilter.sensitiveWords) { // 添加词典
            ahoCorasickFilter.addWord(sensitiveWord);
        }
        ahoCorasickFilter.buildFailurePointer(); // 构建失败指针
        String filteredContent = ahoCorasickFilter.filter(content); // 过滤敏感词
        // ====================结束======================================
        evaluations.setContent(filteredContent); // 将过滤后的敏感词替换原始文本内容
        evaluations.setCommenterId(LocalThreadHolder.getUserId());
        User user = userMapper.getUserById(LocalThreadHolder.getUserId());
        evaluations.setCreateTime(LocalDateTime.now());
        evaluationsMapper.save(evaluations);
        return ApiResult.success("评论成功");
    }

    /**
     * 查询全部评论
     *
     * @return Result<String>
     */
    @Override
    public Result<Object> list(Integer contentId, String contentType) {
        List<CommentParentVO> parentComments = evaluationsMapper.getParentComments(
                contentId,
                LocalThreadHolder.getUserId(),
                contentType
        );
        Integer count = evaluationsMapper.totalCount(contentId, contentType);
        return ApiResult.success(new EvaluationsVO(count, parentComments));
    }

    /**
     * 分页查询评论
     *
     * @return Result<String>
     */
    @Override
    public Result<Object> query(EvaluationsQueryDTO evaluationsQueryDTO) {
        List<CommentChildVO> list = evaluationsMapper.query(evaluationsQueryDTO);
        Integer totalPage = evaluationsMapper.queryCount(evaluationsQueryDTO);
        return ApiResult.success(list, totalPage);
    }

    /**
     * 评论删除
     *
     * @return Result<String>
     */
    @Override
    public Result<String> deleteById(Integer id) {
        ArrayList<Integer> ids = new ArrayList<>();
        ids.add(id);
        evaluationsMapper.batchDelete(ids);
        return ApiResult.success();
    }

    /**
     * 评论修改
     *
     * @return Result<String>
     */
    @Override
    public Result<Void> update(Evaluations evaluations) {
        evaluationsMapper.update(evaluations);
        return ApiResult.success();
    }

    @Override
    public Result<Object> upvoteOperation(Evaluations evaluations) {
        Integer userId = LocalThreadHolder.getUserId();
        Integer evaluationsId = evaluations.getId();

        // 创建查询条件对象
        EvaluationsUpvote query = new EvaluationsUpvote();
        query.setUserId(userId);
        query.setEvaluationsId(evaluationsId);

        // 检查用户是否已点赞
        boolean hasUpvote = evaluationsUpvoteMapper.queryCount(query) > 0;

        if (hasUpvote) {
            // 取消点赞 - 必须同时设置userId和evaluationsId
            EvaluationsUpvote deleteCondition = new EvaluationsUpvote();
            deleteCondition.setUserId(userId);
            deleteCondition.setEvaluationsId(evaluationsId);
            evaluationsUpvoteMapper.delete(deleteCondition);
        } else {
            // 添加点赞
            EvaluationsUpvote newUpvote = new EvaluationsUpvote();
            newUpvote.setUserId(userId);
            newUpvote.setEvaluationsId(evaluationsId);
            evaluationsUpvoteMapper.save(newUpvote);
        }

        // 获取更新后的总点赞数
        EvaluationsUpvote countQuery = new EvaluationsUpvote();
        countQuery.setEvaluationsId(evaluationsId);
        int total = evaluationsUpvoteMapper.queryCount(countQuery);

        // 构建返回结果
        Map<String, Object> rep = new HashMap<>();
        rep.put("count", total);
        rep.put("haveUpvote", !hasUpvote); // 返回操作后的状态

        return ApiResult.success(rep);
    }
}
