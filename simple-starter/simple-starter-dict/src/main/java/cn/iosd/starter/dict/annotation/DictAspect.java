package cn.iosd.starter.dict.annotation;

import cn.iosd.starter.dict.service.DictService;
import cn.iosd.starter.dict.vo.DictItem;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
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

    @Autowired
    private List<DictService> dictServiceList;

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${simple.dict.dictImplBeanName:localDictServiceImpl}")
    private String dictImplBeanName;

    private Map<String, DictService> dictServiceMap = new HashMap<>();

    /**
     * 初始化时将DictService对象与beanName进行关联
     */
    @PostConstruct
    public void init() {
        for (DictService service : dictServiceList) {
            String beanName = applicationContext.getBeanNamesForType(service.getClass())[0];
            dictServiceMap.put(beanName, service);
        }
    }

    public DictService getDictServiceByName(String name) {
        if (StringUtils.isBlank(name)) {
            return dictServiceMap.get(dictImplBeanName);
        }
        return dictServiceMap.get(name);
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
                DictService dictService = getDictServiceByName(dictFieldAnnotation.dictImplBeanName());
                if (dictService == null) {
                    continue;
                }
                // 获取字典项列表
                List<DictItem> dictItemList = getCachedDictItemList(dictService, dictFieldAnnotation.dictionaryParams(), cache);
                if (dictItemList == null || dictItemList.size() == 0) {
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

    /**
     * 获取缓存的字典项列表，如果缓存不存在，则从字典服务中获取，并将结果放入缓存
     *
     * @param dictService 字典服务接口
     * @param dictParams  字典参数
     * @param cache       临时缓存对象
     * @return 字典项列表
     */
    private List<DictItem> getCachedDictItemList(DictService dictService, String dictParams, Map<String, List<DictItem>> cache) {
        List<DictItem> dictItemList = cache.get(dictParams);
        if (dictItemList == null) {
            dictItemList = dictService.getDictItemList(dictParams);
            // 将字典项列表放入缓存
            cache.put(dictParams, dictItemList);
        }
        return dictItemList;
    }

}
