package cn.iosd.utils.common.test;

import cn.iosd.utils.common.TreeListUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author ok1996
 */
public class TreeUtilTest {
    private static final Logger log = LoggerFactory.getLogger(TreeUtilTest.class);

    @Test
    public void testConvert() {
        //乱序数据-正确用法
        performConversionTest(generateOutOfOrderDemoList(), "Convert");
        //异常用法-导致特殊一级节点200为空
        performConversionTest(generateOutOfOrderDemoList(), "ConvertBySequentialGrade");
        //异常用法-导致200的二级节点均成为一级节点
        performConversionTest(generateOutOfOrderDemoList(), "ConvertBySequentialGradeRootCandidateData");
    }

    @Test
    public void testConvertBySequentialGrade() {
        //正确排序数据正确用法-效率一般
        performConversionTest(generateDemoList(), "Convert");
        //正确排序数据正确用法-没有父级关联的数据移除
        performConversionTest(generateDemoList(), "ConvertBySequentialGrade");
        //正确排序数据正确用法-将没有父级关联的数据添加为一级节点-应与调用Convert方法数据结果一致
        performConversionTest(generateDemoList(), "ConvertBySequentialGradeRootCandidateData");
    }

    /**
     * 生成一个乱序的List
     * <p>
     * 特殊一级节点id200 在 二级节点之后
     * </p>
     *
     * @return 列表数据
     */
    private List<Demo> generateOutOfOrderDemoList() {
        List<Demo> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new Demo(i, "Top-level node " + i));
        }
        for (int i = 5; i < 15; i++) {
            list.add(new Demo(i, i % 5, "Second-level node " + i));
        }
        for (int i = 15; i < 100; i++) {
            list.add(new Demo(i, i % 10 + 5, "Third-level node " + i));
        }
        for (int i = 150; i < 160; i++) {
            list.add(new Demo(i, 200, "Second-level node of special top-level node 200 " + i));
        }
        list.add(new Demo(200, "Special top-level node " + 200));
        list.add(new Demo(100, 102, "Illegal node"));
        return list;
    }

    /**
     * 生成一个正序的List
     * <p>
     * 一级数据在二级数据前、二级数据前在三级数据前
     * </p>
     *
     * @return 列表数据
     */
    private List<Demo> generateDemoList() {
        List<Demo> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            list.add(new Demo(i, "Top-level node " + i));
        }
        list.add(new Demo(200, "Special top-level node " + 200));
        for (int i = 5; i < 15; i++) {
            list.add(new Demo(i, i % 5, "Second-level node " + i));
        }
        for (int i = 15; i < 100; i++) {
            list.add(new Demo(i, i % 10 + 5, "Third-level node " + i));
        }
        for (int i = 150; i < 160; i++) {
            list.add(new Demo(i, 200, "Second-level node of special top-level node 200 " + i));
        }
        list.add(new Demo(100, 102, "Illegal node"));
        return list;
    }

    /**
     * 调用转换树型结构测试方法
     *
     * @param list     原始数据
     * @param testName 测试方法
     */
    private void performConversionTest(List<Demo> list, String testName) {
        long startTime = System.currentTimeMillis();
        Predicate<String> isRootPredicate = parentId -> parentId.isEmpty() || "-1".equals(parentId);
        List<Demo> convertData;

        switch (testName) {
            case "ConvertBySequentialGrade":
                convertData = TreeListUtils.convertBySequentialGrade(list, "id", "pid", "children", isRootPredicate, false);
                break;
            case "ConvertBySequentialGradeRootCandidateData":
                convertData = TreeListUtils.convertBySequentialGrade(list, "id", "pid", "children", isRootPredicate, true);
                break;
            default:
                convertData = TreeListUtils.convert(list, "id", "pid", "children", isRootPredicate);
                break;
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        log.info("{} method execution time: {} milliseconds; Top-level tree structure length: {}",
                testName, elapsedTime, convertData.size());
    }


    public static class Demo {
        private Integer id;
        private Integer pid;
        private String name;
        private List<Demo> children;

        public Demo(Integer id, Integer pid, String name) {
            this.id = id;
            this.pid = pid;
            this.name = name;
        }

        public Demo(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getPid() {
            return pid;
        }

        public void setPid(Integer pid) {
            this.pid = pid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Demo> getChildren() {
            return children;
        }

        public void setChildren(List<Demo> children) {
            this.children = children;
        }

        @Override
        public String toString() {
            return "{\"id\":" + id + ",\"pid\":" + pid + ",\"name\":\"" + name + "\",\"children\":" + children + "}";
        }
    }

}
