package cn.zczj.hq.service;

import cn.zczj.hq.mapper.AttrMapper;
import cn.zczj.hq.mapper.ProjectByAttrMapper;
import cn.zczj.hq.pojo.po.Attr;
import cn.zczj.hq.pojo.po.ProjectByAttr;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class ProjectByAttrServiceImpl extends ServiceImpl<ProjectByAttrMapper, ProjectByAttr> implements ProjectByAttrService{
    @Override
    public List<ProjectByAttr> getProjectListByAttrId(Long attrId) {
        LambdaQueryWrapper<ProjectByAttr> wrapper =new LambdaQueryWrapper();
        wrapper.eq(ProjectByAttr::getPrivateEconomyAttrsId, attrId);
        List<ProjectByAttr> list = list(wrapper);
        return list;
    }
}
