package cn.zczj.hq.pojo.dto;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class AdminUserInfoDto {
    private static AdminUserInfoDto adminInstance;
    private AdminUserInfoDto() {
        // 私有构造函数，防止外部实例化
    }

    public static AdminUserInfoDto getAdminInstance() {
        if (adminInstance == null) {
            adminInstance = new AdminUserInfoDto();
        }
        return adminInstance;
    }
    private Long id;
    private String userName;
}
