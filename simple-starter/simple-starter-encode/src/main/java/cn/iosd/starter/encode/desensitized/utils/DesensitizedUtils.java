package cn.iosd.starter.encode.desensitized.utils;

import cn.iosd.starter.encode.desensitized.vo.SensitiveRule;
import org.apache.commons.lang3.StringUtils;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

/**
 * 脱敏工具类
 *
 * @author ok1996
 */
public class DesensitizedUtils {
    /**
     * 用于遮罩的字符串
     */
    private static final String MASK_STR = "*";

    /**
     * 函数式接口
     */
    public static final Map<SensitiveRule, Function<String, String>> desensitizeMap = new EnumMap<>(SensitiveRule.class);

    static {
        desensitizeMap.put(SensitiveRule.CHINESE_NAME, DesensitizedUtils::chineseName);
        desensitizeMap.put(SensitiveRule.ID_CARD, DesensitizedUtils::idCardNum);
        desensitizeMap.put(SensitiveRule.FIXED_PHONE, DesensitizedUtils::fixedPhone);
        desensitizeMap.put(SensitiveRule.MOBILE_PHONE, DesensitizedUtils::mobilePhone);
        desensitizeMap.put(SensitiveRule.ADDRESS, DesensitizedUtils::address);
        desensitizeMap.put(SensitiveRule.EMAIL, DesensitizedUtils::email);
        desensitizeMap.put(SensitiveRule.BANK_CARD, DesensitizedUtils::bankCard);
        desensitizeMap.put(SensitiveRule.PASSWORD, DesensitizedUtils::password);
    }


    /**
     * 对字符串进行脱敏操作
     *
     * @param origin    原始字符串
     * @param prefixLen 左侧需要保留几位明文字段
     * @param suffixLen 右侧需要保留几位明文字段
     * @return 脱敏后结果
     */
    public static String desValue(String origin, int prefixLen, int suffixLen) {
        if (origin == null || origin.isEmpty()) {
            return origin;
        }
        StringBuilder sb = new StringBuilder();
        int len = origin.length();
        for (int i = 0; i < len; i++) {
            if (i < prefixLen || i >= len - suffixLen) {
                sb.append(origin.charAt(i));
            } else {
                sb.append(MASK_STR);
            }
        }
        return sb.toString();
    }

    /**
     * 对字符串进行脱敏操作
     *
     * @param origin    原始字符串
     * @param prefixLen 左侧需要几位密文字段
     * @param suffixLen 右侧需要几位密文字段
     * @return 脱敏后结果
     */
    public static String maskValue(String origin, int prefixLen, int suffixLen) {
        if (origin == null || origin.isEmpty() || (prefixLen + suffixLen) >= origin.length()) {
            return origin;
        }
        int len = origin.length();
        int end = len - suffixLen;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            if (i < prefixLen || i >= end) {
                sb.append(MASK_STR);
            } else {
                sb.append(origin.charAt(i));
            }
        }
        return sb.toString();
    }

    /**
     * 【中文姓名】只显示最后一个汉字，其他隐藏为星号，比如：**梦
     *
     * @param fullName 姓名
     * @return 结果
     */
    public static String chineseName(String fullName) {
        return desValue(fullName, 0, 1);
    }

    /**
     * 【身份证号】显示前六位, 四位，其他隐藏。共计18位或者15位，比如：340304*******1234
     *
     * @param id 身份证号码
     * @return 结果
     */
    public static String idCardNum(String id) {
        return desValue(id, 6, 4);
    }

    /**
     * 【固定电话】后四位，其他隐藏，比如 ****1234
     *
     * @param num 固定电话
     * @return 结果
     */
    public static String fixedPhone(String num) {
        return desValue(num, 0, 4);
    }

    /**
     * 【手机号码】前三位，后四位，其他隐藏，比如135****6810
     *
     * @param num 手机号码
     * @return 结果
     */
    public static String mobilePhone(String num) {
        return desValue(num, 3, 4);
    }

    /**
     * 【地址】只显示到地区，不显示详细地址，比如：北京市海淀区****
     *
     * @param address 地址
     * @return 结果
     */
    public static String address(String address) {
        return desValue(address, 6, 0);
    }

    /**
     * 【电子邮箱 邮箱前缀仅显示第一个字母，前缀其他隐藏，用星号代替，@及后面的地址显示，比如：d**@126.com
     *
     * @param email 电子邮箱
     * @return 结果
     */
    public static String email(String email) {
        if (StringUtils.isBlank(email)) {
            return email;
        }
        int index = StringUtils.indexOf(email, '@');
        if (index <= 1) {
            return email;
        }
        String preEmail = desValue(email.substring(0, index), 1, 0);
        return preEmail + email.substring(index);
    }

    /**
     * 【银行卡号】前六位，后四位，其他用星号隐藏每位1个星号，比如：622260**********1234
     *
     * @param cardNum 银行卡号
     * @return 结果
     */
    public static String bankCard(String cardNum) {
        return desValue(cardNum, 6, 4);
    }

    /**
     * 【密码】密码的全部字符都用*代替，比如：******
     *
     * @param password 密码
     * @return 结果
     */
    public static String password(String password) {
        return "******";
    }

    /**
     * 【密钥】密钥除了最后三位，全部都用*代替，比如：***xdS 脱敏后长度为6，如果明文长度不足三位，则按实际长度显示，剩余位置补*
     *
     * @param key 密钥
     * @return 结果
     */
    public static String key(String key) {
        if (StringUtils.isBlank(key)) {
            return key;
        }
        int viewLength = 6;
        StringBuilder tmpKey = new StringBuilder(desValue(key, 0, 3));
        if (tmpKey.length() > viewLength) {
            return tmpKey.substring(tmpKey.length() - viewLength);
        } else if (tmpKey.length() < viewLength) {
            int buffLength = viewLength - tmpKey.length();
            for (int i = 0; i < buffLength; i++) {
                tmpKey.insert(0, MASK_STR);
            }
            return tmpKey.toString();
        } else {
            return tmpKey.toString();
        }
    }
}
