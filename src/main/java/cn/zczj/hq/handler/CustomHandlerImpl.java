package cn.zczj.hq.handler;

import cn.zczj.hq.enums.LargeCategoryEnum;
import cn.zczj.hq.pojo.po.*;
import cn.zczj.hq.pojo.vo.AttrDetailVO;
import cn.zczj.hq.pojo.vo.PolicyDetailVO;
import cn.zczj.hq.pojo.vo.PolicyVO;
import cn.zczj.hq.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
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
    @Resource
    private RewBatchService rewBatchService;
    @Resource
    private RewPolicyService rewPolicyService;
    @Resource
    private ComAreaService comAreaService;
    @Resource
    private DgSysDeptService dgSysDeptService;
    @Override
    public List<AttrDetailVO> getAttrAndPolicyByCategoryCode(String categoryCode) {
        List<EconomyAttr> economyAttrList = economyAttrService.getAttrByCategoryName(LargeCategoryEnum.getNameByCode(categoryCode));
        if(CollectionUtils.isEmpty(economyAttrList)){
            return new ArrayList<>();
        }
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
            List<PolicyVO> outPolicyList = getOutPolicyList(a.getId());
            List<PolicyVO> policyList = getPolicyList(Long.valueOf(attr.getId()));
            if (!CollectionUtils.isEmpty(outPolicyList)) {
                policyList.addAll(outPolicyList);
            }
            vo.setCount(policyList.size());
            vo.setPolicyList(policyList);
            return vo;
        }).collect(Collectors.toList());
        return attrDetailVOList;
    }

    @Override
    public List<PolicyVO> getOutPolicyList(Long attrId) {
        List<ProjectByAttr> projectListByAttrId = projectByAttrService.getProjectListByAttrId(attrId);
        if(CollectionUtils.isEmpty(projectListByAttrId)){
            return new ArrayList<>();
        }
        List<RewProject> collect = projectListByAttrId.stream().map(o ->{
            LambdaQueryWrapper<RewProject> rewProjectLambdaQueryWrapper =new LambdaQueryWrapper<>();
            rewProjectLambdaQueryWrapper.eq(RewProject::getIsDeleted,0);
            rewProjectLambdaQueryWrapper.eq(RewProject::getId,o.getRewProjectId());
            return rewProjectService.getOne(rewProjectLambdaQueryWrapper);
        }).collect(Collectors.toList());
        List<PolicyVO> policyVOList = new ArrayList<>();
        for (RewProject rewProject : collect) {
            PolicyVO policyVO =new PolicyVO();
            policyVO.setId(rewProject.getId());
            policyVO.setPolicyType(0);
            policyVO.setPolicyName(rewProject.getName());
            LambdaQueryWrapper<RewBatch> lambdaQueryWrapper =new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(RewBatch::getProjectId,rewProject.getId());
            lambdaQueryWrapper.eq(RewBatch::getIsDeleted,0);
            RewBatch rewBatch = rewBatchService.getOne(lambdaQueryWrapper);
            LambdaQueryWrapper<ComArea> comAreaLambdaQueryWrapper=new LambdaQueryWrapper<>();
            comAreaLambdaQueryWrapper.eq(ComArea::getId,rewProject.getAreaId());
            comAreaLambdaQueryWrapper.eq(ComArea::getIsDeleted,0);
            ComArea comArea = comAreaService.getOne(comAreaLambdaQueryWrapper);
            policyVO.setAddress(comArea.getName());
            policyVO.setDeadline(rewBatch.getEndDate());
            policyVO.setEddectiveDate(rewBatch.getStartDate());
            if(rewBatch.getState().equals("ACTIVE")){
                policyVO.setIsReport(true);
            }else {
                policyVO.setIsReport(false);
            }
            // 将 java.util.Date 类型转换为 java.time.LocalDate 类型
            LocalDate start = policyVO.getEddectiveDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate end = policyVO.getDeadline().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            // 使用 ChronoUnit.between() 方法计算日期之间的天数差
            long daysDifference = ChronoUnit.DAYS.between(start, end);
            policyVO.setRemainNum(Math.toIntExact(daysDifference));
            policyVOList.add(policyVO);
        }
        return policyVOList;
    }

    @Override
    public List<PolicyVO> getPolicyList(Long attrId) {
        LambdaQueryWrapper<Policy> policyLambdaQueryWrapper =new LambdaQueryWrapper<>();
        policyLambdaQueryWrapper.eq(Policy::getHqCategoryId, attrId);
        return policyService.list(policyLambdaQueryWrapper).stream().map(o-> {
            PolicyVO policyVO =new PolicyVO();
            policyVO.setId(Long.valueOf(o.getId()));
            policyVO.setPolicyName(o.getPolicyName());
            policyVO.setPolicyType(1);
            policyVO.setAddress(o.getAddress());
            policyVO.setEddectiveDate(o.getEddectiveDate());
            policyVO.setDeadline(o.getDeadline());
            policyVO.setIsReport(false);
            LocalDate start = o.getEddectiveDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate end = o.getDeadline().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            // 使用 ChronoUnit.between() 方法计算日期之间的天数差
            long daysDifference = ChronoUnit.DAYS.between(start, end);
            policyVO.setRemainNum(Math.toIntExact(daysDifference));
            return policyVO;
        }).collect(Collectors.toList());
    }

    @Override
    public PolicyDetailVO getPolicyDetailVO(Integer policyId) {
        Policy policy = policyService.getById(policyId);
        if (policy == null) {
            return null;
        }
        PolicyDetailVO policyDetailVO =new PolicyDetailVO();
        policyDetailVO.setPolicyName(policy.getPolicyName());
        policyDetailVO.setPolicyContent(policy.getPolicyContent());
        policyDetailVO.setPhone(policy.getSupervisorPhone());
        policyDetailVO.setSponsor(policy.getSponsor());
        Attr attr = attrService.getById(policy.getHqCategoryId());
        if (attr != null) {
            EconomyAttr economyAttr = economyAttrService.getById(attr.getReId());
            policyDetailVO.setCategoryName(economyAttr != null ? economyAttr.getAttrName() : "");
        }

        return policyDetailVO;
    }
}
