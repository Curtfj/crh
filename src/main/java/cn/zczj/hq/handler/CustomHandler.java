package cn.zczj.hq.handler;

import cn.zczj.hq.pojo.vo.AttrDetailVO;

import java.util.List;

public interface CustomHandler {
    public List<AttrDetailVO> getAttrAndPolicyByCategoryCode(String categoryCode);
}
