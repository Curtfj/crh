package cn.zczj.hq.controller;

import cn.zczj.hq.pojo.po.Attr;
import cn.zczj.hq.pojo.vo.AttrDetailVO;
import cn.zczj.hq.service.AttrService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/custom")
public class CustomController {
    @Resource
    private AttrService attrService;
    @GetMapping("/attrDetail")
    public AttrDetailVO getAttrDetail(@RequestBody String code){
        //todo 待service方法写完
        return null;
    }
    @GetMapping("/list")
    public List<Attr> getAttrList(){
        List<Attr> attrList = attrService.getAttrList();
        for (Attr attr : attrList) {
            System.out.println(attr);
        }
        return attrService.getAttrList();
    }
}
