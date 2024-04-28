package cn.zczj.hq.service;

import cn.zczj.hq.mapper.AttrMapper;
import cn.zczj.hq.pojo.po.Attr;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AttrServiceImpl extends ServiceImpl<AttrMapper, Attr> implements AttrService {

    @Override
    public List<Attr> getAttrList() {
        return list();
    }

    @Override
    public boolean addAttr(Attr attr) {
        boolean a = save(attr);
        return a;
    }
}
