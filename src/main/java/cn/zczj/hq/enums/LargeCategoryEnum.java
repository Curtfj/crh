package cn.zczj.hq.enums;

import lombok.Data;
import lombok.Getter;

@Getter
public enum LargeCategoryEnum {
    STRENGTHEN("strengthen","强化资源要素支持"),
    ENCOURAGE("encourage","鼓励拓展投资领域"),
    MAINTENANCE("maintenance","维护市场公平竞争"),
    ASSISTANCE("assistance","助力拓市场促升级"),
    BUILD("build","营造最优发展氛围"),
    ;

    LargeCategoryEnum(String categoryCode,String categoryName){
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
    };
    private final String categoryCode;
    private final String categoryName;
    public static String getNameByCode(String categoryCode) {
        for (LargeCategoryEnum largeCategoryEnum : LargeCategoryEnum.values()) {
            if (largeCategoryEnum.getCategoryCode().equals(categoryCode)) {
                return largeCategoryEnum.getCategoryName();
            }
        }
        return null;
    }
}
