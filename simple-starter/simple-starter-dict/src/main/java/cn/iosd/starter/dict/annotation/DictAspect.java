package cn.iosd.starter.dict.annotation;

import cn.iosd.starter.dict.service.DictService;
import cn.iosd.starter.dict.vo.DictItem;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ok1996
 */
@Aspect
@Component
public class DictAspect {
    private static final Logger log = LoggerFactory.getLogger(DictAspect.class);

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
                DictService dictService = getDictServiceByClass(dictFieldAnnotation.dictImplClass());
                List<DictItem> dictItemList = cache.computeIfAbsent(dictFieldAnnotation.dictionaryParams(), dictService::getDictItemList);
                if (dictItemList == null || dictItemList.size() == 0) {
                    log.error("字段：{} 获取字典项列表为空，请检查 字典参数：{} 服务类：{} 服务类包地址：{}",
                            dictFieldAnnotation.relatedField(), dictFieldAnnotation.dictionaryParams(),
                            dictService.getClass().getSimpleName(), dictService.getClass().getPackageName());
                    continue;
                }
                ReflectionUtils.makeAccessible(field);
                // 获取目标字段值
                Object fieldValue = field.get(responseObj);
                // 查找关联字段，翻译字典项
                if (fieldValue != null) {
                    String relatedField = dictFieldAnnotation.relatedField();
                    for (DictItem dictItem : dictItemList) {
                        if (String.valueOf(fieldValue).equals(dictItem.getValue())) {
                            Field related = ReflectionUtils.findField(responseObj.getClass(), relatedField);
                            if (related != null) {
                                ReflectionUtils.makeAccessible(related);
                                ReflectionUtils.setField(related, responseObj, dictItem.getLabel());
                            }
                            break;
                        }
                    }
                }
            } else if (field.isAnnotationPresent(DictEntity.class)) {
                ReflectionUtils.makeAccessible(field);
                Object fieldValue = field.get(responseObj);
                translateDictObjects(fieldValue, cache);
            }
        }
    }

}
