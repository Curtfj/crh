package cn.zczj.hq.handler;

import cn.zczj.hq.pojo.dto.AttrDto;
import cn.zczj.hq.pojo.vo.AttrDetailVO;
import cn.zczj.hq.pojo.vo.CategoryAttrVO;

import java.util.List;

public interface AdminHandler {
    public List<CategoryAttrVO> allCategoryAttr();
    public Boolean saveAttr(AttrDto attrDto);
}
