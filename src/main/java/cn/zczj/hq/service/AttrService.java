package cn.zczj.hq.service;


import cn.zczj.hq.pojo.po.Attr;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface AttrService extends IService<Attr> {
    List<Attr> getAttrList();
    boolean addAttr(Attr attr);
}
