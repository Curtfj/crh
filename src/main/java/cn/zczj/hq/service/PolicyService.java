package cn.zczj.hq.service;

import cn.zczj.hq.pojo.po.Policy;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

public interface PolicyService extends IService<Policy> {
    public Boolean insertPolicy(@Param("policy") Policy policy);
    public Boolean updatePolicy(@Param("policy") Policy policy);
}
