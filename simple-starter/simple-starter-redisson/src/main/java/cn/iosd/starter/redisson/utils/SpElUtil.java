package cn.iosd.starter.redisson.utils;

import jodd.util.StringPool;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Spring Expression Language :SpEL
 *
 * @author ok1996
 */
public class SpElUtil {

    private static final Map<String, Expression> CACHE = new ConcurrentHashMap<>();

    private static final ExpressionParser PARSER = new SpelExpressionParser();

    /**
     * 分析SpEL表达式
     *
     * @param spEl         spel
     * @param argMap       参数映射 {@link #getArgMap(ProceedingJoinPoint)}
     * @param clazz        结果值的类型
     * @param defaultValue 如果spEl解析出来为null的值
     * @return {@link T}
     */
    public static <T> T analytical(final String spEl, final Map<String, Object> argMap, final Class<T> clazz, final T defaultValue) {
        if (!spEl.contains(StringPool.HASH)) {
            return defaultValue;
        }
        Expression expression = CACHE.get(spEl);
        final StandardEvaluationContext context = new StandardEvaluationContext(argMap);
        for (final Map.Entry<String, Object> entry : argMap.entrySet()) {
            context.setVariable(entry.getKey(), entry.getValue());
        }
        if (expression == null) {
            expression = PARSER.parseExpression(spEl);
            CACHE.putIfAbsent(spEl, expression);
        }
        return defVal(expression.getValue(context, clazz), defaultValue);
    }

    static <T> T defVal(T val, T defVal) {
        return val == null ? defVal : val;
    }

    /**
     * 从{@link ProceedingJoinPoint#getArgs()}中得到参数与其字段的kv映射
     *
     * @param point 切点
     * @return {@link HashMap}<{@link String}, {@link Object}>
     */
    public static Map<String, Object> getArgMap(final ProceedingJoinPoint point) {
        final Object[] args = point.getArgs();

        final Map<String, Object> map = new HashMap<>(args.length + 1);
        final Method method = ((MethodSignature) point.getSignature()).getMethod();

        final StandardReflectionParameterNameDiscoverer discoverer = new StandardReflectionParameterNameDiscoverer();
        final String[] params = discoverer.getParameterNames(method);

        if (params != null) {
            for (int len = 0; len < params.length; len++) {
                map.put(params[len], args[len]);
            }
        }
        map.put("RedissonMethodName", method.getName());
        return map;
    }
}
