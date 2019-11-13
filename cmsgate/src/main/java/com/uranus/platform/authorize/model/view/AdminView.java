package com.uranus.platform.authorize.model.view;

import com.uranus.tools.psm.AccessToken;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author 李亚斌
 * @date 2019/07/24 16:32
 * @since v1.1
 */
@Data
@ApiModel(value = "登陆成功后返回给前台的用户数据",description = "登陆成功后返回给前台的用户数据")
public class AdminView {
    @ApiModelProperty("登陆用户名")
    private String username;
    @ApiModelProperty("登陆用户角色列表")
    private List<String> roleNoList;
    @ApiModelProperty("授权密钥")
    private AccessToken accessToken;
    @ApiModelProperty("登陆方式，默认为Web浏览器方式登录")
    private String loginType;
    @ApiModelProperty(value = "是否为首次登陆",dataType="boolean")
    private boolean firstLogin;

}
