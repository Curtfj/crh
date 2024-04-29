package cn.zczj.hq.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("\"rew_batch\"")
public class RewBatch {
    private Long id;
    private Date endDate;
    private Long projectId;
    private Date startDate;
    private String state;
    private Integer isDeleted;
}
