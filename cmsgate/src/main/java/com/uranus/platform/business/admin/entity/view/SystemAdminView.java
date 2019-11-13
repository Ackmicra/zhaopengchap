package com.uranus.platform.business.admin.entity.view;

import com.uranus.platform.business.admin.entity.convert.AccountStatus;
import com.uranus.tools.beans.annotation.ConvertBean;
import com.uranus.tools.beans.annotation.ConvertField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

/**
 * @author 李亚斌
 * @date 2019/07/25 17:05
 * @since v1.1
 */
@Data
@ConvertBean
@ApiModel(value = "管理员用户实体",description = "管理员消息对象")
public class SystemAdminView {
    @ApiModelProperty(value = "管理员用户名",dataType = "String",required = true)
    @NotBlank(message = "用户名不得为空")
    @Length(max = 12,min = 6,message = "用户名长度应在6-12之间")
    private String username;
    @ApiModelProperty(value = "管理员密码",dataType = "String",required = true)
    private String password;
    @ApiModelProperty(value = "注册时间",dataType = "long")
    @ConvertField(targetField="regtime",convertName = "timeStampToLocalDateTime")
    private long regtime;
    @ApiModelProperty(value = "积分",dataType = "int",required = false)
    private int integral;
    @ApiModelProperty(value = "管理员账户状态",dataType = "String")
    @ConvertField(targetField = "status",convertName = "accountStatusConvert",enumConverter = AccountStatus.AccountStatusConverter.class)
    private String status;
}
