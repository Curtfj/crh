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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
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
        List<PolicyVO> policyList = new ArrayList<>();
        List<AttrDetailVO> attrDetailVOList = new ArrayList<>();
        for (EconomyAttr a : economyAttrList) {
            AttrDetailVO vo = new AttrDetailVO();
            Attr attr = collect.get(a.getId());
            vo.setId(a.getId());
            if(attr != null){
                vo.setMeasure(attr.getMeasure());
                vo.setSituation(attr.getSituation());
                vo.setStage(attr.getStage());
                policyList = getPolicyList(Long.valueOf(attr.getId()));
            }

            vo.setName(a.getAttrName());

            List<PolicyVO> outPolicyList = getOutPolicyList(a.getId());

            if (!CollectionUtils.isEmpty(outPolicyList)) {
                policyList.addAll(outPolicyList);
            }
            vo.setCount(policyList.size());
            vo.setPolicyList(policyList);
            attrDetailVOList.add(vo);
            policyList = new ArrayList<>();
        }
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
            policyVO.setProcessType(rewProject.getProcessType());
            LambdaQueryWrapper<RewBatch> lambdaQueryWrapper =new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(RewBatch::getProjectId,rewProject.getId());
            lambdaQueryWrapper.eq(RewBatch::getIsDeleted,0);
            lambdaQueryWrapper.eq(RewBatch::getState,"ACTIVE");
            RewBatch rewBatch = rewBatchService.getOne(lambdaQueryWrapper);
            if (rewBatch == null) {
                LambdaQueryWrapper<RewBatch> wrapper =new LambdaQueryWrapper<>();
                wrapper.eq(RewBatch::getProjectId,rewProject.getId());
                wrapper.eq(RewBatch::getIsDeleted,0);
                List<RewBatch> list = rewBatchService.list(wrapper);
                if (!CollectionUtils.isEmpty(list)) {
                    rewBatch = list.get(0);
                }
            }
            LambdaQueryWrapper<ComArea> comAreaLambdaQueryWrapper=new LambdaQueryWrapper<>();
            comAreaLambdaQueryWrapper.eq(ComArea::getId,rewProject.getAreaId());
            comAreaLambdaQueryWrapper.eq(ComArea::getIsDeleted,0);
            ComArea comArea = comAreaService.getOne(comAreaLambdaQueryWrapper);
            policyVO.setAddress(comArea.getName());
            if (rewBatch != null) {
                policyVO.setBatchId(rewBatch.getId());
                policyVO.setDeadline(rewBatch.getEndDate());
                policyVO.setEddectiveDate(rewBatch.getStartDate());
                if(rewBatch.getState().equals("ACTIVE")){
                    policyVO.setIsReport(true);
                }else {
                    policyVO.setIsReport(false);
                }
                // 将 java.util.Date 类型转换为 java.time.LocalDate 类型
                LocalDateTime currentTime = LocalDateTime.now();
                LocalDateTime end = policyVO.getDeadline().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                if(currentTime.isAfter(end)){
                    policyVO.setRemainNum(0);
                }else {
                    // 使用 ChronoUnit.between() 方法计算日期之间的天数差
                    long daysDifference = ChronoUnit.DAYS.between(currentTime, end);
                    policyVO.setRemainNum(Math.toIntExact(daysDifference)+1);
                }
            }

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
