package cn.iosd.utils.common.test.id;

import cn.iosd.utils.common.id.IdFactory;
import cn.iosd.utils.common.id.IdGenerate;
import cn.iosd.utils.common.test.id.impl.IdGenerateCustomImpl;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class IdFactoryTest {
    private static final Logger log = LoggerFactory.getLogger(IdFactoryTest.class);

    /**
     * 使用默认实现工厂获取主键
     */
    @Test
    public void testGenerate() {
        Long generatedId = IdFactory.generate();
        log.info("IdFactory.generate():{}", generatedId);
        assertNotNull(generatedId);

        String generatedIdStr = IdFactory.generateStr();
        log.info("IdFactory.generateStr():{}", generatedIdStr);
        assertNotNull(generatedIdStr);
    }

    /**
     * 自定义实现IdGenerate，初始化工厂使用;
     * 完成后清除自定义实现，重新调用默认实现
     */
    @Test
    public void testGenerateCustomImpl() {
        Supplier<IdGenerate> impl = IdGenerateCustomImpl::new;
        IdFactory.initialize(impl);
        assertEquals(IdFactory.generateStr(), "test");
        assertEquals(IdFactory.generate(), 1L);

        IdFactory.clear();
        testGenerate();
    }

    /**
     * 已经过初始化调用后，在未执行IdFactory.clear()时，再次初始化将抛出异常
     */
    @Test
    public void testGenerateThrowException() {
        assertThrows(IllegalStateException.class, () -> {
            IdFactory.generateStr();
            Supplier<IdGenerate> impl = IdGenerateCustomImpl::new;
            IdFactory.initialize(impl);
        });
    }
}
