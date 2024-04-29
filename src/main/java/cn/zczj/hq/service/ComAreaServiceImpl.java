package cn.zczj.hq.service;

import cn.zczj.hq.mapper.AttrMapper;
import cn.zczj.hq.mapper.ComAreaMapper;
import cn.zczj.hq.pojo.po.Attr;
import cn.zczj.hq.pojo.po.ComArea;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ComAreaServiceImpl extends ServiceImpl<ComAreaMapper, ComArea> implements ComAreaService {
}
