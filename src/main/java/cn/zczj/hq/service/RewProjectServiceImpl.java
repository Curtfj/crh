package cn.zczj.hq.service;

import cn.zczj.hq.mapper.RewProjectMapper;
import cn.zczj.hq.pojo.po.RewProject;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RewProjectServiceImpl extends ServiceImpl<RewProjectMapper, RewProject> implements RewProjectService {
}
