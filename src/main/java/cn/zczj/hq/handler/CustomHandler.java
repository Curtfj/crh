package cn.zczj.hq.handler;

import cn.zczj.hq.pojo.vo.AttrDetailVO;
import cn.zczj.hq.pojo.vo.PolicyDetailVO;
import cn.zczj.hq.pojo.vo.PolicyVO;

import java.util.List;

public interface CustomHandler {
    public List<AttrDetailVO> getAttrAndPolicyByCategoryCode(String categoryCode);
    public List<PolicyVO> getOutPolicyList(Long attrId);
    public List<PolicyVO> getPolicyList(Long attrId);
    PolicyDetailVO getPolicyDetailVO(Integer policyId);

}
