package cn.zczj.hq.service;

import cn.zczj.hq.pojo.po.EconomyAttr;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface EconomyAttrService extends IService<EconomyAttr> {
    public List<EconomyAttr> getAttrByCategoryName(String categoryName);
}
