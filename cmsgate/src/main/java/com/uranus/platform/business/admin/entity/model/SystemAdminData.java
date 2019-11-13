package com.uranus.platform.business.admin.entity.model;

import com.uranus.platform.business.admin.entity.convert.AccountStatus;
import com.uranus.tools.beans.annotation.ConvertBean;
import com.uranus.tools.beans.annotation.ConvertField;
import com.uranus.tools.beans.converter.CommonConverter;
import lombok.Data;

@Data
@ConvertBean
public class SystemAdminData {
    private Integer id;

    private String username;

    private String password;
    @ConvertField(targetField="regtime",commonConverter = CommonConverter.TIMESTAMP_TO_LOCALDATETIME)
    private Long regtime;

    private Integer integral;
    @ConvertField(targetField = "status",convertName = "accountStatusConvert",enumConverter = AccountStatus.AccountStatusConverter.class)
    private String status;
}