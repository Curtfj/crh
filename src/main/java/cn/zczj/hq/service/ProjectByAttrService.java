package cn.zczj.hq.service;

import cn.zczj.hq.pojo.po.ProjectByAttr;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ProjectByAttrService extends IService<ProjectByAttr> {
    public List<ProjectByAttr> getProjectListByAttrId(Long attrId);
}
