package cn.zczj.hq.service;

import cn.zczj.hq.mapper.PolicyMapper;
import cn.zczj.hq.pojo.po.Policy;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PolicyServiceImpl extends ServiceImpl<PolicyMapper, Policy> implements PolicyService {
    @Resource
    private PolicyMapper policyMapper;
    @Override
    public Boolean insertPolicy(Policy policy) {
        int i = policyMapper.insertPolicy(policy);
        if(i>0){
            return true;
        }
        return false;
    }

    @Override
    public Boolean updatePolicy(Policy policy) {
        int i = policyMapper.updatePolicy(policy);
        if(i>0){
            return true;
        }
        return false;
    }
}
