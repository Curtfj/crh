package cn.zczj.hq.service;


import cn.zczj.hq.pojo.po.Attr;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface AttrService extends IService<Attr> {
    public Boolean insertAttr(Attr attr);

    public Boolean updateAttr(Attr attr);
}
