package cn.iosd.starter.dict.annotation;

import cn.iosd.starter.dict.service.DictService;
import cn.iosd.starter.dict.vo.DictItem;
import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger log = LoggerFactory.getLogger(DictAspect.class);

    @Autowired
    private List<DictService> dictServiceList;

    @Autowired
    private ApplicationContext applicationContext;

    @Value("${simple.dict.dictImplBeanName:}")
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

    /**
     * 根据字典服务名称获取对应的字典服务
     * <br/>
     * 优先级1：注解 @DictField 配置值 dictImplBeanName
     * <br/>
     * 优先级2：配置文件项 simple.dict.dictImplBeanName
     * <br/>
     * 优先级3：代码中服务类@Order优先顺序第一的服务类
     *
     * @param name 字典服务实现类名称
     * @return 字典服务
     */
    public DictService getDictServiceByName(String name) {
        String serviceName = StringUtils.isNotBlank(name) ? name : dictImplBeanName;
        return dictServiceMap.getOrDefault(serviceName, dictServiceList.get(0));
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
                List<DictItem> dictItemList = getCachedDictItemList(dictService, dictFieldAnnotation.dictionaryParams(), cache);
                if (dictItemList == null || dictItemList.size() == 0) {
                    log.error("获取字典项列表为空，请检查服务类：{} 字典参数：{} 服务类包地址：{}"
                            , dictService.getClass().getSimpleName(), dictFieldAnnotation.dictionaryParams()
                            , dictService.getClass().getPackageName());
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
            cache.put(dictParams, dictItemList);
        }
        return dictItemList;
    }

}
