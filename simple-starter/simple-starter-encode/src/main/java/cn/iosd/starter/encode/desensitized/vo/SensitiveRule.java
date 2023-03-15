package cn.iosd.starter.encode.desensitized.vo;

/**
 * 脱敏规则
 *
 * @author ok1996
 */
public enum SensitiveRule {
    /**
     * 中文名
     */
    CHINESE_NAME,
    /**
     * 身份证号
     */
    ID_CARD,
    /**
     * 座机号
     */
    FIXED_PHONE,
    /**
     * 手机号
     */
    MOBILE_PHONE,
    /**
     * 地址
     */
    ADDRESS,
    /**
     * 电子邮件
     */
    EMAIL,
    /**
     * 银行卡
     */
    BANK_CARD,
    /**
     * 密码
     */
    PASSWORD,
    /**
     * 自定义
     */
    CUSTOM
}
