package cn.zczj.hq.service;

import cn.zczj.hq.mapper.EconomyAttrMapper;
import cn.zczj.hq.pojo.po.EconomyAttr;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EconomyAttrServiceImpl extends ServiceImpl<EconomyAttrMapper, EconomyAttr> implements EconomyAttrService {
    @Override
    public List<EconomyAttr> getAttrByCategoryName(String categoryName) {
        LambdaQueryWrapper<EconomyAttr> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(EconomyAttr::getAttrCategory,categoryName);
        List<EconomyAttr> list = list(queryWrapper);
        if(list.size()>0){
            return list;
        }
        return null;
    }
}
