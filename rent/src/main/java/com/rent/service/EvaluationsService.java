package com.rent.service;

import com.rent.pojo.api.Result;
import com.rent.pojo.dto.EvaluationsQueryDTO;
import com.rent.pojo.entity.Evaluations;

/**
 * 评论服务接口
 */
public interface EvaluationsService {

    Result<Object> insert(Evaluations evaluations);

    Result<Object> list(Integer contentId, String contentType);

    Result<Object> query(EvaluationsQueryDTO evaluationsQueryDto);

    Result<String> deleteById(Integer id);

    Result<Void> update(Evaluations evaluations);

    Result<Object> upvoteOperation(Evaluations evaluations);

}
