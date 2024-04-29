package cn.zczj.hq.mapper;

import cn.zczj.hq.pojo.po.Attr;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AttrMapper extends BaseMapper<Attr> {
    public int insertAttr(@Param("attr") Attr attr);

    public int updateAttr(@Param("attr") Attr attr);

}
