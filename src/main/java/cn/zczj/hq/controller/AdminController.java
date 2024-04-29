package cn.zczj.hq.controller;

import cn.zczj.hq.common.Result;
import cn.zczj.hq.handler.AdminHandler;
import cn.zczj.hq.handler.CustomHandler;
import cn.zczj.hq.pojo.dto.AttrDto;
import cn.zczj.hq.pojo.po.Attr;
import cn.zczj.hq.pojo.vo.AttrDetailVO;
import cn.zczj.hq.pojo.vo.CategoryAttrVO;
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
    public Result addAttr(@RequestBody AttrDto attrDto){
        if(adminHandler.saveAttr(attrDto)){
           return Result.success();
        }
        return Result.fail(501,"数据库插入失败");
    }
}