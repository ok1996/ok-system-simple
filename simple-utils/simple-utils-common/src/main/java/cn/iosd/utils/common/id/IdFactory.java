package cn.iosd.utils.common.id;

import cn.iosd.utils.common.id.impl.SimpleIdGenerate;

import java.util.function.Supplier;

/**
 * @author ok1996
 */
public final class IdFactory {
    private static volatile IdGenerate idGenerateInstance;

    /**
     * 静态内部类实现懒加载的单例模式
     */
    private static class SingletonHolder {
        private static final IdGenerate INSTANCE = new SimpleIdGenerate();
    }

    private IdFactory() {
    }

    /**
     * 初始化 IdFactory，设置生成唯一标识符的实例-自定义接口实现
     *
     * @param generateIdSupplier 生成唯一标识符的供应商
     * @throws IllegalStateException 如果生成唯一标识符的实例已存在，则抛出异常。在重新初始化前，应该先清除对象。
     */
    public static void initialize(Supplier<IdGenerate> generateIdSupplier) {
        if (idGenerateInstance != null) {
            throw new IllegalStateException("GenerateId object is not null. To reinitialize, clear the object first.");
        }
        if (generateIdSupplier != null) {
            idGenerateInstance = generateIdSupplier.get();
        }
    }

    public static void clear() {
        idGenerateInstance = null;
    }

    public static IdGenerate getInstance() {
        if (idGenerateInstance == null) {
            synchronized (IdFactory.class) {
                if (idGenerateInstance == null) {
                    initialize(() -> SingletonHolder.INSTANCE);
                }
            }
        }
        return idGenerateInstance;
    }
}