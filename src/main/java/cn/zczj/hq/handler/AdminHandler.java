package cn.zczj.hq.handler;

import cn.zczj.hq.pojo.dto.AttrDto;
import cn.zczj.hq.pojo.dto.PolicyDto;
import cn.zczj.hq.pojo.vo.AttrDetailVO;
import cn.zczj.hq.pojo.vo.AttrVO;
import cn.zczj.hq.pojo.vo.CategoryAttrVO;

import java.util.List;

public interface AdminHandler {
    public List<CategoryAttrVO> allCategoryAttr();
    public Boolean saveAttr(AttrDto attrDto);
    public Boolean savePolicy(PolicyDto policyDto);
    public List<AttrVO> attrList();
}
