package cn.zczj.hq.handler;

import cn.zczj.hq.enums.LargeCategoryEnum;
import cn.zczj.hq.pojo.dto.AdminUserInfoDto;
import cn.zczj.hq.pojo.dto.AttrDto;
import cn.zczj.hq.pojo.dto.PolicyDto;
import cn.zczj.hq.pojo.po.Attr;
import cn.zczj.hq.pojo.po.EconomyAttr;
import cn.zczj.hq.pojo.po.Policy;
import cn.zczj.hq.pojo.vo.*;
import cn.zczj.hq.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@Component
public class AdminHandlerImpl implements AdminHandler {
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
    public List<CategoryAttrVO> allCategoryAttr() {
        List<String> collect = Arrays.stream(LargeCategoryEnum.values()).map(a -> a.getCategoryName()).collect(Collectors.toList());
        List<CategoryAttrVO> categoryAttrVOList = new ArrayList<>();
        for (String s : collect) {
            List<EconomyAttr> attrByCategoryName = economyAttrService.getAttrByCategoryName(s);
            CategoryAttrVO categoryAttrVO = new CategoryAttrVO();
            categoryAttrVO.setName(s);
            categoryAttrVO.setAttrList(
                    attrByCategoryName.stream().map(o -> {
                        CategoryAttrVO.AttrVO attrVO = new CategoryAttrVO.AttrVO();
                        attrVO.setId(o.getId());
                        attrVO.setAttrName(o.getAttrName());
                        return attrVO;
                    }).collect(Collectors.toList()));
            categoryAttrVOList.add(categoryAttrVO);
        }
        return categoryAttrVOList;
    }

    @Override
    public Boolean saveAttr(AttrDto attrDto) {
        Attr attr = new Attr();
        if (attrDto.getReId() == null) {
            return false;
        }
        attr.setMeasure(!StringUtils.isEmpty(attrDto.getMeasure()) ? attrDto.getMeasure() : "");
        attr.setSituation(!StringUtils.isEmpty(attrDto.getSituation()) ? attrDto.getSituation() : "");
        attr.setStage(!StringUtils.isEmpty(attrDto.getStage()) ? attrDto.getStage() : "");
        AdminUserInfoDto adminUserInfoDto = AdminUserInfoDto.getAdminInstance();
        attr.setReId(attrDto.getReId());
        LambdaQueryWrapper<Attr> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Attr::getReId, attrDto.getReId());
        List<Attr> list = attrService.list(lambdaQueryWrapper);
        if (list.size() > 0) {
            attr.setUpdateBy(adminUserInfoDto.getId());
            attr.setUpdateTime(new Date());
            attr.setId(list.get(0).getId());
            return attrService.updateAttr(attr);
        }
        attr.setCreateBy(adminUserInfoDto.getId());
        attr.setCreateTime(new Date());
        attr.setUpdateBy(adminUserInfoDto.getId());
        attr.setUpdateTime(new Date());
        return attrService.insertAttr(attr);

    }

    @Override
    public Boolean savePolicy(PolicyDto policyDto) {
        Policy policy = new Policy();
        policy.setAddress(policyDto.getAddress());
        policy.setPolicyContent(policyDto.getPolicyContent());
        policy.setPolicyName(policyDto.getPolicyName());
        policy.setDeadline(policyDto.getDeadline());
        policy.setEddectiveDate(policyDto.getEddectiveDate());
        policy.setHqCategoryId(policyDto.getHqCategoryId());
        policy.setSponsor(policyDto.getSponsor());
        policy.setSupervisorPhone(policyDto.getSupervisorPhone());
        AdminUserInfoDto adminUserInfoDto = AdminUserInfoDto.getAdminInstance();
        if (policyDto.getId() != null) {
            policy.setUpdateBy(adminUserInfoDto.getId());
            policy.setUpdateTime(new Date());
            policy.setId(policyDto.getId());
            return policyService.updatePolicy(policy);
        }
        policy.setCreateBy(adminUserInfoDto.getId());
        policy.setCreateTime(new Date());
        policy.setUpdateBy(adminUserInfoDto.getId());
        policy.setUpdateTime(new Date());
        return policyService.insertPolicy(policy);
    }

    @Override
    public List<AttrVO> attrList() {
        List<AttrVO> collect = attrService.list().stream().map(o -> {
            AttrVO attrVO = new AttrVO();
            attrVO.setId(Long.valueOf(o.getReId()));
            EconomyAttr byId = economyAttrService.getById(o.getReId());
            if (byId != null) {
                attrVO.setName(byId.getAttrName());
            }
            return attrVO;
        }).collect(Collectors.toList());
        if (collect != null) {
            return collect;
        }
        return new ArrayList<>();
    }

    @Override
    public AttrDetailAdminVO getAttrDetailByReId(Long reId) {
        LambdaQueryWrapper<Attr> attrLambdaQueryWrapper = new LambdaQueryWrapper<>();
        attrLambdaQueryWrapper.eq(Attr::getReId, reId);
        Attr attr = attrService.getOne(attrLambdaQueryWrapper);
        AttrDetailAdminVO attrDetailAdminVO = new AttrDetailAdminVO();
        if (attr != null) {
            attrDetailAdminVO.setMeasure(attr.getMeasure());
            attrDetailAdminVO.setSituation(attr.getSituation());
            attrDetailAdminVO.setStage(attr.getStage());
        } else {
            attrDetailAdminVO.setSituation("");
            attrDetailAdminVO.setMeasure("");
            attrDetailAdminVO.setStage("");
        }
        return attrDetailAdminVO;
    }

    @Override
    public List<AdminPolicyVO> getAllPolicyList(Long reId) {
        LambdaQueryWrapper<Policy> policyLambdaQueryWrapper = new LambdaQueryWrapper<>();
        policyLambdaQueryWrapper.eq(Policy::getHqCategoryId, reId);
        List<AdminPolicyVO> collect = policyService.list(policyLambdaQueryWrapper).stream().map(o -> {
            AdminPolicyVO adminPolicyVO = new AdminPolicyVO();
            BeanUtils.copyProperties(o, adminPolicyVO);
            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime end = adminPolicyVO.getDeadline().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            if (currentTime.isAfter(end)) {
                adminPolicyVO.setRemainNum(0);
            } else {
                // 使用 ChronoUnit.between() 方法计算日期之间的天数差
                long daysDifference = ChronoUnit.DAYS.between(currentTime, end);
                adminPolicyVO.setRemainNum(Math.toIntExact(daysDifference) + 1);
            }
            return adminPolicyVO;
        }).collect(Collectors.toList());
        if (collect != null) {
            return collect;
        }
        return new ArrayList<>();
    }

    @Override
    public AdminPolicyVO getPolicyDetail(Integer id) {
        LambdaQueryWrapper<Policy> policyLambdaQueryWrapper =new LambdaQueryWrapper<>();
        policyLambdaQueryWrapper.eq(Policy::getId,id);
        Policy one = policyService.getOne(policyLambdaQueryWrapper);
        if (one != null) {
            AdminPolicyVO adminPolicyVO = new AdminPolicyVO();
            BeanUtils.copyProperties(one, adminPolicyVO);
            LocalDateTime currentTime = LocalDateTime.now();
            LocalDateTime end = adminPolicyVO.getDeadline().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            if (currentTime.isAfter(end)) {
                adminPolicyVO.setRemainNum(0);
            } else {
                // 使用 ChronoUnit.between() 方法计算日期之间的天数差
                long daysDifference = ChronoUnit.DAYS.between(currentTime, end);
                adminPolicyVO.setRemainNum(Math.toIntExact(daysDifference) + 1);
            }
            return adminPolicyVO;
        }
        return new AdminPolicyVO();
    }
}
