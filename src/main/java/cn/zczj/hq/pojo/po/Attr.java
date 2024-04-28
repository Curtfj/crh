package cn.zczj.hq.pojo.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@TableName("\"zczj_attr\"")
@AllArgsConstructor
@NoArgsConstructor
public class Attr {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String measure;
    private String situation;
    private String stage;
    private Long reId;
    private Long createBy;
    private Date createTime;
    private Long updateBy;
    private Date updateTime;
}
