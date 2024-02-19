package cn.iosd.utils.common.id;

import cn.iosd.utils.common.id.impl.SimpleIdGenerate;

import java.util.function.Supplier;

/**
 * @author ok1996
 */
public final class IdFactory {
    /**
     * 默认的 IdGenerate 实现类
     */
    private static final Supplier<IdGenerate> DEFAULT_GENERATOR = SimpleIdGenerate::new;

    private static volatile IdGenerate idGenerateInstance;

    private IdFactory() {
    }

    private static IdGenerate getInstance() {
        if (idGenerateInstance == null) {
            synchronized (IdFactory.class) {
                if (idGenerateInstance == null) {
                    initialize(DEFAULT_GENERATOR);
                }
            }
        }
        return idGenerateInstance;
    }

    public static void initialize(Supplier<IdGenerate> idGenerateSupplier) {
        if (idGenerateInstance != null) {
            throw new IllegalStateException("GenerateId object is not null. To reinitialize, clear the object first.");
        }
        idGenerateInstance = idGenerateSupplier.get();
    }

    public static void clear() {
        idGenerateInstance = null;
    }

    /**
     * 生成Long类型唯一主键
     *
     * @return 主键
     */
    public static Long generate() {
        return getInstance().generate();
    }

    /**
     * 生成String类型唯一主键
     *
     * @return 主键
     */
    public static String generateStr() {
        return getInstance().generateStr();
    }
}
