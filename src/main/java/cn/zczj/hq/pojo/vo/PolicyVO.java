package cn.zczj.hq.pojo.vo;

import lombok.Data;
import lombok.extern.java.Log;

import java.util.Date;
@Data
public class PolicyVO {
    private Integer id;
    private Integer policyType;
    private String policyName;
    private String address;
    private Date eddectiveDate;
    private Date deadline;
    private Integer remainNum;
}
