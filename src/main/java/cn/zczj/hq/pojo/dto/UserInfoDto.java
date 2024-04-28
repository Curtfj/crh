package cn.zczj.hq.pojo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class UserInfoDto {
    private static UserInfoDto instance;
    private UserInfoDto() {
        // 私有构造函数，防止外部实例化
    }

    public static UserInfoDto getInstance() {
        if (instance == null) {
            instance = new UserInfoDto();
        }
        return instance;
    }
    private Long id;
    private String userName;
}
