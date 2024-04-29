package cn.zczj.hq.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("\"dg_sys_dept\"")
public class DgSysDept {
    private Long id;
    private Integer isDeleted;
    private String aliasName;
}
