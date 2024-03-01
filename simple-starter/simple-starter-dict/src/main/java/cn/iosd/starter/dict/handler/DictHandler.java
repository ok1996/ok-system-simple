package cn.iosd.starter.dict.handler;

import cn.iosd.starter.dict.annotation.DictEntity;
import cn.iosd.starter.dict.annotation.DictField;
import cn.iosd.starter.dict.service.DictService;
import cn.iosd.starter.dict.vo.DictItem;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author ok1996
 */
@Aspect
@Component
@ConditionalOnProperty(name = "simple.dict.enabled", havingValue = "true", matchIfMissing = true)
public class DictHandler {
    private static final Logger log = LoggerFactory.getLogger(DictHandler.class);

    @Autowired
    private List<DictService> dictServiceList;

    /**
     * 根据字典服务泛型获取对应的字典服务
     * <br/>
     * 优先级1：注解 @DictField 配置值 dictImplClass
     * <br/>
     * 优先级2：代码中服务类@Order优先顺序第一的服务类
     *
     * @param dictClass 字典服务实现类
     * @return 字典服务
     */
    public DictService getDictServiceByClass(Class<? extends DictService> dictClass) {
        return dictServiceList.stream()
                .filter(dictClass::isInstance)
                .findFirst()
                .orElse(dictServiceList.get(0));
    }


    @Around("@annotation(cn.iosd.starter.dict.annotation.Dict)")
    public Object translateDict(ProceedingJoinPoint joinPoint) throws Throwable {
        Object responseObj = joinPoint.proceed();
        translateDictObjects(responseObj, new HashMap<>(16));
        return responseObj;
    }


    /**
     * 将对象进行字典转换
     *
     * @param responseObj 对象
     * @param cache       临时缓存数据
     * @throws IllegalAccessException
     */
    private void translateDictObjects(Object responseObj, Map<String, List<DictItem>> cache) throws IllegalAccessException {
        if (responseObj instanceof List<?>) {
            for (Object singleObj : (List<?>) responseObj) {
                translateDictObjects(singleObj, cache);
            }
        }

        Field[] fields = responseObj.getClass().getDeclaredFields();
        for (Field field : fields) {
            DictField dictFieldAnnotation = field.getAnnotation(DictField.class);
            if (dictFieldAnnotation != null) {
                processDictField(responseObj, field, cache, dictFieldAnnotation);
            } else if (field.isAnnotationPresent(DictEntity.class)) {
                ReflectionUtils.makeAccessible(field);
                Object fieldValue = field.get(responseObj);
                translateDictObjects(fieldValue, cache);
            }
        }
    }

    /**
     * 处理字典字段的翻译。
     *
     * @param responseObj     要处理的对象
     * @param field           被注解的字段
     * @param cache           字典项缓存
     * @param fieldAnnotation 字典字段的注解
     * @throws IllegalAccessException 反射操作时可能抛出的异常
     */
    private void processDictField(Object responseObj, Field field, Map<String, List<DictItem>> cache, DictField fieldAnnotation) throws IllegalAccessException {
        ReflectionUtils.makeAccessible(field);
        Object fieldValue = field.get(responseObj);
        if (ObjectUtils.isEmpty(fieldValue)) {
            log.debug("目标字段值为空跳过查询，字段：{}，对象：{}", field.getName(), responseObj.toString());
            return;
        }
        String relatedField = fieldAnnotation.relatedField();
        Field related = ReflectionUtils.findField(responseObj.getClass(), relatedField);
        if (related == null) {
            log.debug("需要设置翻译字段值不存在：{}，将跳过查询，对象：{}", relatedField, responseObj.getClass().getName());
            return;
        }
        DictService dictService = getDictServiceByClass(fieldAnnotation.dictImplClass());
        List<DictItem> dictItemList = cache.computeIfAbsent(fieldAnnotation.dictionaryParams(), dictService::getDictItemList);
        if (dictItemList == null || dictItemList.isEmpty()) {
            log.debug("字段：{} 获取字典项列表为空，请检查 字典参数：{} 服务类：{} 服务类包地址：{}",
                    fieldAnnotation.relatedField(), fieldAnnotation.dictionaryParams(),
                    dictService.getClass().getSimpleName(), dictService.getClass().getPackageName());
            return;
        }
        // 查找关联字段，翻译字典项
        Optional<DictItem> matchingDictItem = dictItemList.stream()
                .filter(dictItem -> String.valueOf(fieldValue).equals(dictItem.getValue()))
                .findFirst();

        matchingDictItem.map(DictItem::getLabel)
                .ifPresent(label -> {
                    ReflectionUtils.makeAccessible(related);
                    ReflectionUtils.setField(related, responseObj, label);
                });
    }
}
