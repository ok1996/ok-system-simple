package cn.iosd.starter.redisson.utils;

import cn.iosd.starter.redisson.domain.MethodContext;
import jodd.util.StringPool;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.StandardReflectionParameterNameDiscoverer;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * Spring Expression Language :SpEL
 *
 * @author ok1996
 */
public class SpElUtil {
    private static final ExpressionParser PARSER = new SpelExpressionParser();

    /**
     * 解析并执行 SpEL 表达式。
     * 若表达式中包含哈希符号 (#)，则解析并执行该表达式；
     * 否则，直接返回默认值。
     *
     * @param spEl         SpEL表达式字符串。
     * @param argMap       方法参数的映射，用于在表达式中引用参数。
     * @param clazz        表达式期望返回的结果类型。
     * @param defaultValue 如果表达式未执行或执行结果为空时的默认返回值。
     * @return 解析执行后的结果，或默认值。
     */
    public static <T> T analytical(final String spEl, final Map<String, Object> argMap, final Class<T> clazz, final T defaultValue) {
        if (!spEl.contains(StringPool.HASH)) {
            return defaultValue;
        }
        Expression expression = PARSER.parseExpression(spEl);
        StandardEvaluationContext context = new StandardEvaluationContext();
        argMap.forEach(context::setVariable);

        return Optional.of(new CachedExpression(expression, context))
                .map(cachedExpression -> cachedExpression.expression().getValue(cachedExpression.context(), clazz))
                .orElse(defaultValue);
    }


    /**
     * 从方法调用的切点中获取参数映射并封装对象
     *
     * @param point 方法调用的切点
     * @return 包含方法参数和实例字符串的 MethodContext 对象
     */
    public static MethodContext getArgMap(final ProceedingJoinPoint point) {
        final Object[] args = point.getArgs();
        final Method method = ((MethodSignature) point.getSignature()).getMethod();
        final String[] params = new StandardReflectionParameterNameDiscoverer().getParameterNames(method);
        if (params == null) {
            throw new IllegalArgumentException("方法参数无法获取");
        }
        Map<String, Object> map = new LinkedHashMap<>();
        IntStream.range(0, args.length).forEach(i -> map.put(params[i], args[i]));
        return new MethodContext(map, method.toGenericString());
    }

    /**
     * 缓存辅助类，用于存储解析后的SpEL表达式
     */
    private record CachedExpression(Expression expression, StandardEvaluationContext context) {
    }
}
