package com.uranus.platform.business.admin.controller;

import com.uranus.platform.business.admin.entity.bo.SystemAdminDomain;
import com.uranus.platform.business.admin.entity.view.SystemAdminView;
import com.uranus.platform.business.admin.service.SystemAuthorizeService;
import com.uranus.platform.utils.status.CmsBusinessStatus;
import com.uranus.tools.beans.BeanCopyUtils;
import com.uranus.tools.psm.ResponseEntity;
import com.uranus.tools.psm.status.BusinessStatus;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 李亚斌
 * @date 2019/07/26 10:43
 * @since v1.1
 */
@Api(value = "管理员操作API文档",tags = {"管理员","授权模块"},description = "用于管理员模块的增删改查")
@RestController
@RequestMapping("/systemAdmin")
public class SystemAdminController {
    @Autowired
    private SystemAuthorizeService systemAuthorizeService;

    @ApiOperation(value = "新增管理员用户",tags = {"新增","授权模块"})
    @PostMapping
    public ResponseEntity<SystemAdminView> addNewSystemAdmin(@RequestBody SystemAdminView systemAdminView){
        SystemAdminDomain systemAdminDomain = BeanCopyUtils.INSTANCE.convertTo(systemAdminView,SystemAdminDomain.class);
        if(systemAuthorizeService.addNewAdmin(systemAdminDomain)){
            return ResponseEntity.Success.<SystemAdminView>OK(BusinessStatus.ADD_FAIL).response();
//            return ResponseEntity.Success.CREATED(systemAdminDomain).response().convertTo(SystemAdminView.class);
        }
        return ResponseEntity.Success.<SystemAdminView>OK(CmsBusinessStatus.FANGKUANSHIBAI).successMessage("新增失败").response();
    }

    @ApiOperation(value = "查询管理员用户",tags = {"查询","授权模块"})
    @ApiImplicitParams(
            @ApiImplicitParam(value = "管理员姓名",name = "username",paramType = "path",dataType = "String",required = true)
    )
    @GetMapping("/{username}")
    public ResponseEntity<SystemAdminView> findSystemAdmin(@PathVariable String username){
        SystemAdminDomain systemAdminDomain = systemAuthorizeService.findAdminByUsername(username);
        return ResponseEntity.Success.OK(systemAdminDomain).response().convertTo(SystemAdminView.class);
    }

    @ApiOperation(value = "查询全部管理员数据",tags = {"查询","授权模块"})
    @GetMapping("/findAll")
    public ResponseEntity<List<SystemAdminView>> findAllAdmin(){
        List<SystemAdminDomain> adminDomainList = systemAuthorizeService.findAllSystemAdmin();
        return ResponseEntity.Success.OK(BeanCopyUtils.INSTANCE.convertToAsList(adminDomainList,SystemAdminView.class)).response();
    }

    @ApiOperation(value = "分页查询实例",tags = {"查询","授权模块"})
    @ApiImplicitParams({
            @ApiImplicitParam(value = "管理员查询条件",name = "adminView",dataType = "object",required = false,paramType = "form"),
            @ApiImplicitParam(value = "当前页", name = "pageNum",dataType = "int",required = true,paramType = "query"),
            @ApiImplicitParam(value = "每页大小", name = "pageSize",dataType = "int",required = true,paramType = "query")
    })
    @PostMapping("/findByPage")
    public ResponseEntity<List<SystemAdminView>> findAdminByPage(@RequestBody SystemAdminView adminView,
                                                                 @RequestParam int pageNum,
                                                                 @RequestParam int pageSize) {
        SystemAdminDomain adminDomain = BeanCopyUtils.INSTANCE.convertTo(adminView,SystemAdminDomain.class);
        List<SystemAdminDomain> adminDomainList = systemAuthorizeService.findAllByPage(adminDomain,pageNum,pageSize);
        List<SystemAdminView> adminViewList = BeanCopyUtils.INSTANCE.convertToAsList(adminDomainList,SystemAdminView.class);
        return ResponseEntity.Success.OK(adminViewList).response();
    }
}
