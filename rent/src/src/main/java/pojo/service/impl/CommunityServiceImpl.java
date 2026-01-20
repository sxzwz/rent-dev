package pojo.service.impl;

import pojo.entity.Community;
import pojo.mapper.CommunityMapper;
import pojo.service.ICommunityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 小区信息表 服务实现类
 * </p>
 *
 * @author syk
 * @since 2025-12-17
 */
@Service
public class CommunityServiceImpl extends ServiceImpl<CommunityMapper, Community> implements ICommunityService {

}
