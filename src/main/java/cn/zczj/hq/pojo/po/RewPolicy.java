package cn.zczj.hq.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("\"rew_policy\"")
public class RewPolicy {
    private Long id;
    private Integer isDeleted;
    private String content;
    private String contact;
}
