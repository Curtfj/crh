package cn.zczj.hq.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("\"rew_project\"")
public class RewProject {
    private Long id;
    private String name;
}
