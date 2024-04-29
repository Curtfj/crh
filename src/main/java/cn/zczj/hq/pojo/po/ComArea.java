package cn.zczj.hq.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("\"com_area\"")
public class ComArea {
    private Long id;
    private Integer isDeleted;
    private String name;
}
