package cn.zczj.hq.service;

import cn.zczj.hq.mapper.AttrMapper;
import cn.zczj.hq.pojo.po.Attr;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AttrServiceImpl extends ServiceImpl<AttrMapper, Attr> implements AttrService {
    @Resource
    private AttrMapper attrMapper;
    @Override
    public Boolean insertAttr(Attr attr) {
        int i = attrMapper.insertAttr(attr);
        if(i>0){
            return true;
        }
        return false;
    }

    @Override
    public Boolean updateAttr(Attr attr) {
        int i = attrMapper.updateAttr(attr);
        if(i>0){
            return true;
        }
        return false;
    }
}
