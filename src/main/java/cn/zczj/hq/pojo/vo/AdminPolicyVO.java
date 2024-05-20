package cn.zczj.hq.pojo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class AdminPolicyVO {
    private Integer id;
    private String policyName;
    private String policyContent;
    /**
     * 本系统二级分类id
     */
    private Integer hqCategoryId;
    private String supervisorPhone;
    private String sponsor;
    private String address;
    private Date eddectiveDate;
    private Date deadline;
    private Integer remainNum;
}
