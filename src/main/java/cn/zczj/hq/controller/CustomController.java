package cn.zczj.hq.controller;

import cn.zczj.hq.common.Result;
import cn.zczj.hq.enums.LargeCategoryEnum;
import cn.zczj.hq.handler.CustomHandler;
import cn.zczj.hq.pojo.po.Attr;
import cn.zczj.hq.pojo.vo.AttrDetailVO;
import cn.zczj.hq.pojo.vo.PolicyDetailVO;
import cn.zczj.hq.service.AttrService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/custom")
public class CustomController {
    @Resource
    private CustomHandler customHandler;
    @GetMapping("/attrDetail")
    public Result<List<AttrDetailVO>> getAttrDetail(@RequestParam String categoryCode){
        return Result.success(customHandler.getAttrAndPolicyByCategoryCode(categoryCode));
    }
    @GetMapping("/policyDetail")
    public Result<PolicyDetailVO> getPolicyDetail(@RequestParam Integer policyId){
        return Result.success(customHandler.getPolicyDetailVO(policyId));
    }
}
