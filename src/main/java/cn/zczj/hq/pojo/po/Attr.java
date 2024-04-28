package cn.zczj.hq.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("\"zczj_attr\"")
public class Attr {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String measure;
    private String situation;
    private String stage;
    private Long reId;
}
