package cn.iosd.base.dict.service.mapper;

import cn.iosd.base.dict.api.vo.DictGroupVo;
import cn.iosd.base.dict.service.entity.DictInfoEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 字典数据 Mapper 接口
 * </p>
 *
 * @author ok1996
 */
public interface DictInfoMapper extends BaseMapper<DictInfoEntity> {

    /**
     * 提取已存在数据的字典分组
     *
     * @return 字典分组数据
     */
    List<DictGroupVo> getUniqueDictGroups();
}
