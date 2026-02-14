package com.rent.controller;




import com.rent.aop.Pager;
import com.rent.pojo.api.ApiResult;
import com.rent.pojo.api.Result;
import com.rent.pojo.entity.Notice;
import com.rent.pojo.vo.NoticeListItemVO;
import com.rent.pojo.vo.NoticeQueryDTO;
import com.rent.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 公告控制器
 */
@RestController
@RequestMapping("/notice")
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    /**
     * 新增公告信息
     */
    @PostMapping(value = "/save")
    public Result<String> save(@RequestBody Notice notice) {
        return noticeService.saveEntity(notice);
    }


    /**
     * 修改公告信息
     */
    @PutMapping(value = "/update")
    public Result<String> update(@RequestBody Notice notice) {
        return noticeService.updateEntity(notice);
    }


    /**
     * 删除公告信息
     */
    @DeleteMapping(value = "/{id}")
    public Result<String> delete(@PathVariable Integer id) {
        noticeService.removeById(id);
        return ApiResult.success("公告删除成功");
    }

    /**
     * 通过ID查询资讯详情信息
     */
    @GetMapping(value = "/getById/{id}")
    public Result<Notice> getById(@PathVariable Integer id) {
        return noticeService.selectById(id);
    }

    /**
     * 查询公告信息
     *
     * @param noticeQueryDTO 查询参数
     * @return Result<List < NoticeListItemVO>> 响应结果
     */
    @Pager
    @PostMapping(value = "/list")
    public Result<List<NoticeListItemVO>> list(@RequestBody NoticeQueryDTO noticeQueryDTO) {
        return noticeService.list(noticeQueryDTO);
    }

}