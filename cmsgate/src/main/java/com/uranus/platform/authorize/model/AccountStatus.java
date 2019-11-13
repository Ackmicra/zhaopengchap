package com.uranus.platform.authorize.model;

/**
 * @author 李亚斌
 * @date 2019/01/28 13:46
 * @since v1.1
 */
public enum AccountStatus {
    /**
     * 初始化
     */
    INITIAL,
    /**
     * 正常
     */
    NORMAL,
    /**
     * 被锁定
     */
    LOCKED,
    /**
     * 被封禁
     */
    BANNED,
    /**
     * 不可用
     */
    DISABLE
}
