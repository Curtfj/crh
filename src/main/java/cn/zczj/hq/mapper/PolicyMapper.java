package cn.zczj.hq.mapper;

import cn.zczj.hq.pojo.po.Attr;
import cn.zczj.hq.pojo.po.Policy;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PolicyMapper extends BaseMapper<Policy> {
    public int insertPolicy(@Param("policy") Policy policy);

    public int updatePolicy(@Param("policy") Policy policy);
}
