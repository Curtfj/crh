package cn.zczj.hq.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("\"rew_project_private_economy_attrs\"")
public class ProjectByAttr {
    private Long rewProjectId;
    private Long privateEconomyAttrsId;
}
