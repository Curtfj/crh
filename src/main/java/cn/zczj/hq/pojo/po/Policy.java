package cn.zczj.hq.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 政策
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("\"zczj_policy\"")
public class Policy {
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
    private Long createBy;
    private Date createTime;
    private Long updateBy;
    private Date updateTime;

}
