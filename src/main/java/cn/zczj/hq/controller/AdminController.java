package cn.zczj.hq.controller;

import cn.zczj.hq.common.Result;
import cn.zczj.hq.handler.AdminHandler;
import cn.zczj.hq.handler.CustomHandler;
import cn.zczj.hq.pojo.dto.AttrDto;
import cn.zczj.hq.pojo.dto.PolicyDto;
import cn.zczj.hq.pojo.po.Attr;
import cn.zczj.hq.pojo.vo.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    @Resource
    private AdminHandler adminHandler;
    @GetMapping("/allCategoryAttr")
    public Result<List<CategoryAttrVO>>  allCategoryAttr(){
       return Result.success(adminHandler.allCategoryAttr());
    }
    @PostMapping("/saveAttr")
    public Result saveAttr(@RequestBody AttrDto attrDto){
        if(adminHandler.saveAttr(attrDto)){
           return Result.success();
        }
        return Result.fail(501,"数据库插入失败");
    }
    @PostMapping("/savePolicy")
    public Result savePolicy(@RequestBody PolicyDto policyDto){
        if(adminHandler.savePolicy(policyDto)){
            return Result.success();
        }
        return Result.fail(501,"数据库插入失败");
    }
    @GetMapping("/sys/attrList")
    public Result<List<AttrVO>> systemAttr(){
        return Result.success(adminHandler.attrList());
    }
    @GetMapping("/attrDetail")
    public Result<AttrDetailAdminVO> getAttrDetail(@RequestParam Long reId){
        return Result.success(adminHandler.getAttrDetailByReId(reId));
    }
    @GetMapping("/getAllPolicy")
    public Result<List<AdminPolicyVO>> getAllPolicy(@RequestParam Long reId){
        return Result.success(adminHandler.getAllPolicyList(reId));
    }
}
