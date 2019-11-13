package com.uranus.platform.authorize.model.domain;

import com.uranus.platform.authorize.model.RequestMethodType;
import com.uranus.platform.authorize.model.convert.RequestMethodTypeEnumConverter;
import com.uranus.tools.beans.annotation.ConvertField;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 李亚斌
 * @date 2019/03/18 16:41
 * @since v1.1
 */
@Data
public class PermissionDomain implements Serializable {
    @ConvertField(targetField = "permNo")
    private String permName;
    @ConvertField(targetField = "permName")
    private String permChName;
    @ConvertField(targetField = "uriExpression")
    private String uriExpression;
    @ConvertField(targetField = "requestMethodType",convertName = "requestMethodTypeConvert",enumConverter = RequestMethodTypeEnumConverter.class)
    private RequestMethodType requestMethodType;
    private List<RoleDomain> roleList;

    public PermissionDomain(String permName) {
        this.permName = permName;
    }

    public PermissionDomain() {
    }
}