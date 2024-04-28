package cn.zczj.hq.pojo.vo;

import cn.zczj.hq.pojo.po.Policy;
import lombok.Data;

import java.util.List;

@Data
public class AttrDetailVO {
    private Integer id;
    private String name;
    private Integer count;
    private String measure;
    private String situation;
    private String stage;
    private List<PolicyVO> policyList;
}
