package cn.iosd.utils.common.id;

/**
 * @author ok1996
 */
public interface IdGenerate {
    /**
     * 生成Long类型唯一主键
     *
     * @return 主键
     */
    Long generate();


    /**
     * 生成String类型唯一主键
     *
     * @return 主键
     */
    String generateStr();
}
