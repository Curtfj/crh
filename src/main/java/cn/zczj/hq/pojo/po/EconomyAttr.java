package cn.zczj.hq.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("\"com_private_economy_attr\"")
public class EconomyAttr {
    private Long id;
    private String attrCategory;
    private String attrName;
}
