package cn.zczj.hq.handler;

import cn.zczj.hq.enums.LargeCategoryEnum;
import cn.zczj.hq.pojo.po.Attr;
import cn.zczj.hq.pojo.po.EconomyAttr;
import cn.zczj.hq.pojo.vo.AttrDetailVO;
import cn.zczj.hq.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Component
public class CustomHandlerImpl implements CustomHandler {
    @Resource
    private AttrService attrService;
    @Resource
    private EconomyAttrService economyAttrService;
    @Resource
    private PolicyService policyService;
    @Resource
    private RewProjectService rewProjectService;
    @Resource
    private ProjectByAttrService projectByAttrService;

    @Override
    public List<AttrDetailVO> getAttrAndPolicyByCategoryCode(String categoryCode) {
        List<EconomyAttr> economyAttrList = economyAttrService.getAttrByCategoryName(LargeCategoryEnum.getNameByCode(categoryCode));
        List<Long> relIdList = economyAttrList.stream().map(EconomyAttr::getId).collect(Collectors.toList());
        LambdaQueryWrapper<Attr> in = new LambdaQueryWrapper<Attr>().in(Attr::getReId, relIdList);
        List<Attr> list = attrService.list(in);
        Map<Long, Attr> collect = list.stream().collect(toMap(Attr::getReId, Function.identity()));
        List<AttrDetailVO> attrDetailVOList = economyAttrList.stream().map(a -> {
            AttrDetailVO vo = new AttrDetailVO();
            Attr attr = collect.get(a.getId());
            vo.setId(attr.getId());
            vo.setName(a.getAttrName());

            vo.setMeasure(attr.getMeasure());
            vo.setSituation(attr.getSituation());
            vo.setStage(attr.getStage());
            //todo 待三级写好
//            vo.setCount(0);
//            vo.setPolicyList();
            return vo;

        }).collect(Collectors.toList());
        return attrDetailVOList;
    }
}
