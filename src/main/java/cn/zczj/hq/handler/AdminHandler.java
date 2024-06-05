package cn.zczj.hq.handler;

import cn.zczj.hq.pojo.dto.AttrDto;
import cn.zczj.hq.pojo.dto.PolicyDto;
import cn.zczj.hq.pojo.vo.*;

import java.util.List;

public interface AdminHandler {
    public List<CategoryAttrVO> allCategoryAttr();

    public Boolean saveAttr(AttrDto attrDto);

    public Boolean savePolicy(PolicyDto policyDto);

    public List<AttrVO> attrList();

    AttrDetailAdminVO getAttrDetailByReId(Long reId);

    List<AdminPolicyVO> getAllPolicyList(Long reId);

    AdminPolicyVO getPolicyDetail(Integer id);
}
