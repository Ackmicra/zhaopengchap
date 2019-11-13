package com.uranus.platform.authorize.model.domain;

import com.uranus.tools.beans.annotation.ConvertField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 李亚斌
 * @date 2019/03/18 16:35
 * @since v1.1
 */
@Data
public class RoleDomain implements Serializable {
    @ConvertField(targetField = "roleNo")
    private final String roleName;
    @ConvertField(targetField = "roleName")
    private String roleChName;
    private String roleType;

    private List<AdminDomain> adminList;
    private List<PermissionDomain> permList;
}
