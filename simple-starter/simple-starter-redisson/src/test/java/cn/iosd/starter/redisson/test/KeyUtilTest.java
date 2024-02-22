package cn.iosd.starter.redisson.test;

import cn.iosd.starter.redisson.utils.KeyUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class KeyUtilTest {
    @Mock
    private ProceedingJoinPoint point;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this).close();
    }

    @Test
    void testGenerate() throws Exception {
        Object[] args = {"value1", "value2"};
        when(point.getArgs()).thenReturn(args);

        MethodSignature methodSignature = Mockito.mock(MethodSignature.class);
        when(point.getSignature()).thenReturn(methodSignature);

        Method method = KeyUtil.class.getDeclaredMethod("generate", ProceedingJoinPoint.class, String.class, String.class, boolean.class);
        when(methodSignature.getMethod()).thenReturn(method);

        String resultMd5 = KeyUtil.generate(point, "staticPart", "dynamicPart", true);
        String successResultMd5 = "staticPart:PDV:a334ed280287b9a75be7bb3c7527f245";
        assertEquals(successResultMd5, resultMd5);

        String result = KeyUtil.generate(point, "staticPart", "#point", false);
        String successResult = "staticPart:value1";
        assertEquals(successResult, result);

        String resultNu = KeyUtil.generate(point, "", "", false);
        String resultNuResult = "SDV:PDV";
        assertEquals(resultNuResult, resultNu);
    }
}
