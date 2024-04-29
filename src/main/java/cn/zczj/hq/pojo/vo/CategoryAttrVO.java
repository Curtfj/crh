package cn.zczj.hq.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryAttrVO {

    private String name;
    private List<AttrVO> attrVOList;
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AttrVO{
        private Long id;
        private String attrName;
    }

}
