package com.uranus.platform.business.admin.entity.model;

import lombok.Data;

@Data
public class SystemRoleData {
    private Long id;

    private String roleName;

    private String roleChName;

    private String roleType;

    private Long adminId;

}