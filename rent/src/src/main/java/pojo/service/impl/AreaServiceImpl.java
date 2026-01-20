package pojo.service.impl;

import pojo.entity.Area;
import pojo.mapper.AreaMapper;
import pojo.service.IAreaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 地区信息表 服务实现类
 * </p>
 *
 * @author syk
 * @since 2025-12-17
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements IAreaService {

}
