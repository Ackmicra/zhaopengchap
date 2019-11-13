package com.uranus.platform.business.admin.entity.convert;

import com.uranus.tools.beans.converter.AbstractEnumConverter;
import com.uranus.tools.beans.function.EnumConvertFunction;

/**
 * @author 李亚斌
 * @date 2019/07/26 9:49
 * @since v1.1
 */
public enum AccountStatus implements EnumConvertFunction {
    NORMAL("1"),
    LOCKED("2")
    ;


    private String statusValue;

    AccountStatus(String statusValue) {
        this.statusValue = statusValue;
    }

    @Override
    public String getStringValue() {
        return this.statusValue;
    }

    public static class AccountStatusConverter extends AbstractEnumConverter<AccountStatus> {

    }
}
