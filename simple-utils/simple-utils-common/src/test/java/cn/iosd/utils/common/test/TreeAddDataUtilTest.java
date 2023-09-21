package cn.iosd.utils.common.test;

import cn.iosd.utils.common.TreeListUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author ok1996
 */
public class TreeAddDataUtilTest {
    private static final Logger log = LoggerFactory.getLogger(TreeAddDataUtilTest.class);

    @Test
    public void testConvert() {
        Map<String, Data> idData = new HashMap<>();
        idData.put("0", new Data(0, "ce0"));
        idData.put("1", new Data(1, "ce1"));
        idData.put("10", new Data(10, "ce10"));
        idData.put("20", new Data(20, "ce20"));
        //乱序数据-正确用法
        String result = performConversionTest(generateOutOfOrderDemoList(), idData);
        String successResult="[{\"id\":0,\"pid\":null,\"name\":\"Top-level node 0\",\"children\":[{\"id\":5,\"pid\":0,\"name\":\"Second-level node 5\",\"children\":[{\"id\":20,\"pid\":5,\"name\":\"Third-level node 20\",\"children\":null,\"data\":{\"id\":20,\"other\":\"ce20\"}}, {\"id\":30,\"pid\":5,\"name\":\"Third-level node 30\",\"children\":null,\"data\":null}, {\"id\":40,\"pid\":5,\"name\":\"Third-level node 40\",\"children\":null,\"data\":null}, {\"id\":50,\"pid\":5,\"name\":\"Third-level node 50\",\"children\":null,\"data\":null}, {\"id\":60,\"pid\":5,\"name\":\"Third-level node 60\",\"children\":null,\"data\":null}, {\"id\":70,\"pid\":5,\"name\":\"Third-level node 70\",\"children\":null,\"data\":null}, {\"id\":80,\"pid\":5,\"name\":\"Third-level node 80\",\"children\":null,\"data\":null}, {\"id\":90,\"pid\":5,\"name\":\"Third-level node 90\",\"children\":null,\"data\":null}],\"data\":null}, {\"id\":10,\"pid\":0,\"name\":\"Second-level node 10\",\"children\":[{\"id\":15,\"pid\":10,\"name\":\"Third-level node 15\",\"children\":null,\"data\":null}, {\"id\":25,\"pid\":10,\"name\":\"Third-level node 25\",\"children\":null,\"data\":null}, {\"id\":35,\"pid\":10,\"name\":\"Third-level node 35\",\"children\":null,\"data\":null}, {\"id\":45,\"pid\":10,\"name\":\"Third-level node 45\",\"children\":null,\"data\":null}, {\"id\":55,\"pid\":10,\"name\":\"Third-level node 55\",\"children\":null,\"data\":null}, {\"id\":65,\"pid\":10,\"name\":\"Third-level node 65\",\"children\":null,\"data\":null}, {\"id\":75,\"pid\":10,\"name\":\"Third-level node 75\",\"children\":null,\"data\":null}, {\"id\":85,\"pid\":10,\"name\":\"Third-level node 85\",\"children\":null,\"data\":null}, {\"id\":95,\"pid\":10,\"name\":\"Third-level node 95\",\"children\":null,\"data\":null}],\"data\":{\"id\":10,\"other\":\"ce10\"}}],\"data\":{\"id\":0,\"other\":\"ce0\"}}, {\"id\":1,\"pid\":null,\"name\":\"Top-level node 1\",\"children\":[{\"id\":6,\"pid\":1,\"name\":\"Second-level node 6\",\"children\":[{\"id\":21,\"pid\":6,\"name\":\"Third-level node 21\",\"children\":null,\"data\":null}, {\"id\":31,\"pid\":6,\"name\":\"Third-level node 31\",\"children\":null,\"data\":null}, {\"id\":41,\"pid\":6,\"name\":\"Third-level node 41\",\"children\":null,\"data\":null}, {\"id\":51,\"pid\":6,\"name\":\"Third-level node 51\",\"children\":null,\"data\":null}, {\"id\":61,\"pid\":6,\"name\":\"Third-level node 61\",\"children\":null,\"data\":null}, {\"id\":71,\"pid\":6,\"name\":\"Third-level node 71\",\"children\":null,\"data\":null}, {\"id\":81,\"pid\":6,\"name\":\"Third-level node 81\",\"children\":null,\"data\":null}, {\"id\":91,\"pid\":6,\"name\":\"Third-level node 91\",\"children\":null,\"data\":null}],\"data\":null}, {\"id\":11,\"pid\":1,\"name\":\"Second-level node 11\",\"children\":[{\"id\":16,\"pid\":11,\"name\":\"Third-level node 16\",\"children\":null,\"data\":null}, {\"id\":26,\"pid\":11,\"name\":\"Third-level node 26\",\"children\":null,\"data\":null}, {\"id\":36,\"pid\":11,\"name\":\"Third-level node 36\",\"children\":null,\"data\":null}, {\"id\":46,\"pid\":11,\"name\":\"Third-level node 46\",\"children\":null,\"data\":null}, {\"id\":56,\"pid\":11,\"name\":\"Third-level node 56\",\"children\":null,\"data\":null}, {\"id\":66,\"pid\":11,\"name\":\"Third-level node 66\",\"children\":null,\"data\":null}, {\"id\":76,\"pid\":11,\"name\":\"Third-level node 76\",\"children\":null,\"data\":null}, {\"id\":86,\"pid\":11,\"name\":\"Third-level node 86\",\"children\":null,\"data\":null}, {\"id\":96,\"pid\":11,\"name\":\"Third-level node 96\",\"children\":null,\"data\":null}],\"data\":null}],\"data\":{\"id\":1,\"other\":\"ce1\"}}, {\"id\":2,\"pid\":null,\"name\":\"Top-level node 2\",\"children\":[{\"id\":7,\"pid\":2,\"name\":\"Second-level node 7\",\"children\":[{\"id\":22,\"pid\":7,\"name\":\"Third-level node 22\",\"children\":null,\"data\":null}, {\"id\":32,\"pid\":7,\"name\":\"Third-level node 32\",\"children\":null,\"data\":null}, {\"id\":42,\"pid\":7,\"name\":\"Third-level node 42\",\"children\":null,\"data\":null}, {\"id\":52,\"pid\":7,\"name\":\"Third-level node 52\",\"children\":null,\"data\":null}, {\"id\":62,\"pid\":7,\"name\":\"Third-level node 62\",\"children\":null,\"data\":null}, {\"id\":72,\"pid\":7,\"name\":\"Third-level node 72\",\"children\":null,\"data\":null}, {\"id\":82,\"pid\":7,\"name\":\"Third-level node 82\",\"children\":null,\"data\":null}, {\"id\":92,\"pid\":7,\"name\":\"Third-level node 92\",\"children\":null,\"data\":null}],\"data\":null}, {\"id\":12,\"pid\":2,\"name\":\"Second-level node 12\",\"children\":[{\"id\":17,\"pid\":12,\"name\":\"Third-level node 17\",\"children\":null,\"data\":null}, {\"id\":27,\"pid\":12,\"name\":\"Third-level node 27\",\"children\":null,\"data\":null}, {\"id\":37,\"pid\":12,\"name\":\"Third-level node 37\",\"children\":null,\"data\":null}, {\"id\":47,\"pid\":12,\"name\":\"Third-level node 47\",\"children\":null,\"data\":null}, {\"id\":57,\"pid\":12,\"name\":\"Third-level node 57\",\"children\":null,\"data\":null}, {\"id\":67,\"pid\":12,\"name\":\"Third-level node 67\",\"children\":null,\"data\":null}, {\"id\":77,\"pid\":12,\"name\":\"Third-level node 77\",\"children\":null,\"data\":null}, {\"id\":87,\"pid\":12,\"name\":\"Third-level node 87\",\"children\":null,\"data\":null}, {\"id\":97,\"pid\":12,\"name\":\"Third-level node 97\",\"children\":null,\"data\":null}],\"data\":null}],\"data\":null}, {\"id\":3,\"pid\":null,\"name\":\"Top-level node 3\",\"children\":[{\"id\":8,\"pid\":3,\"name\":\"Second-level node 8\",\"children\":[{\"id\":23,\"pid\":8,\"name\":\"Third-level node 23\",\"children\":null,\"data\":null}, {\"id\":33,\"pid\":8,\"name\":\"Third-level node 33\",\"children\":null,\"data\":null}, {\"id\":43,\"pid\":8,\"name\":\"Third-level node 43\",\"children\":null,\"data\":null}, {\"id\":53,\"pid\":8,\"name\":\"Third-level node 53\",\"children\":null,\"data\":null}, {\"id\":63,\"pid\":8,\"name\":\"Third-level node 63\",\"children\":null,\"data\":null}, {\"id\":73,\"pid\":8,\"name\":\"Third-level node 73\",\"children\":null,\"data\":null}, {\"id\":83,\"pid\":8,\"name\":\"Third-level node 83\",\"children\":null,\"data\":null}, {\"id\":93,\"pid\":8,\"name\":\"Third-level node 93\",\"children\":null,\"data\":null}],\"data\":null}, {\"id\":13,\"pid\":3,\"name\":\"Second-level node 13\",\"children\":[{\"id\":18,\"pid\":13,\"name\":\"Third-level node 18\",\"children\":null,\"data\":null}, {\"id\":28,\"pid\":13,\"name\":\"Third-level node 28\",\"children\":null,\"data\":null}, {\"id\":38,\"pid\":13,\"name\":\"Third-level node 38\",\"children\":null,\"data\":null}, {\"id\":48,\"pid\":13,\"name\":\"Third-level node 48\",\"children\":null,\"data\":null}, {\"id\":58,\"pid\":13,\"name\":\"Third-level node 58\",\"children\":null,\"data\":null}, {\"id\":68,\"pid\":13,\"name\":\"Third-level node 68\",\"children\":null,\"data\":null}, {\"id\":78,\"pid\":13,\"name\":\"Third-level node 78\",\"children\":null,\"data\":null}, {\"id\":88,\"pid\":13,\"name\":\"Third-level node 88\",\"children\":null,\"data\":null}, {\"id\":98,\"pid\":13,\"name\":\"Third-level node 98\",\"children\":null,\"data\":null}],\"data\":null}],\"data\":null}, {\"id\":4,\"pid\":null,\"name\":\"Top-level node 4\",\"children\":[{\"id\":9,\"pid\":4,\"name\":\"Second-level node 9\",\"children\":[{\"id\":24,\"pid\":9,\"name\":\"Third-level node 24\",\"children\":null,\"data\":null}, {\"id\":34,\"pid\":9,\"name\":\"Third-level node 34\",\"children\":null,\"data\":null}, {\"id\":44,\"pid\":9,\"name\":\"Third-level node 44\",\"children\":null,\"data\":null}, {\"id\":54,\"pid\":9,\"name\":\"Third-level node 54\",\"children\":null,\"data\":null}, {\"id\":64,\"pid\":9,\"name\":\"Third-level node 64\",\"children\":null,\"data\":null}, {\"id\":74,\"pid\":9,\"name\":\"Third-level node 74\",\"children\":null,\"data\":null}, {\"id\":84,\"pid\":9,\"name\":\"Third-level node 84\",\"children\":null,\"data\":null}, {\"id\":94,\"pid\":9,\"name\":\"Third-level node 94\",\"children\":null,\"data\":null}],\"data\":null}, {\"id\":14,\"pid\":4,\"name\":\"Second-level node 14\",\"children\":[{\"id\":19,\"pid\":14,\"name\":\"Third-level node 19\",\"children\":null,\"data\":null}, {\"id\":29,\"pid\":14,\"name\":\"Third-level node 29\",\"children\":null,\"data\":null}, {\"id\":39,\"pid\":14,\"name\":\"Third-level node 39\",\"children\":null,\"data\":null}, {\"id\":49,\"pid\":14,\"name\":\"Third-level node 49\",\"children\":null,\"data\":null}, {\"id\":59,\"pid\":14,\"name\":\"Third-level node 59\",\"children\":null,\"data\":null}, {\"id\":69,\"pid\":14,\"name\":\"Third-level node 69\",\"children\":null,\"data\":null}, {\"id\":79,\"pid\":14,\"name\":\"Third-level node 79\",\"children\":null,\"data\":null}, {\"id\":89,\"pid\":14,\"name\":\"Third-level node 89\",\"children\":null,\"data\":null}, {\"id\":99,\"pid\":14,\"name\":\"Third-level node 99\",\"children\":null,\"data\":null}],\"data\":null}],\"data\":null}, {\"id\":200,\"pid\":null,\"name\":\"Special top-level node 200\",\"children\":[{\"id\":150,\"pid\":200,\"name\":\"Second-level node of special top-level node 200 150\",\"children\":null,\"data\":null}, {\"id\":151,\"pid\":200,\"name\":\"Second-level node of special top-level node 200 151\",\"children\":null,\"data\":null}, {\"id\":152,\"pid\":200,\"name\":\"Second-level node of special top-level node 200 152\",\"children\":null,\"data\":null}, {\"id\":153,\"pid\":200,\"name\":\"Second-level node of special top-level node 200 153\",\"children\":null,\"data\":null}, {\"id\":154,\"pid\":200,\"name\":\"Second-level node of special top-level node 200 154\",\"children\":null,\"data\":null}, {\"id\":155,\"pid\":200,\"name\":\"Second-level node of special top-level node 200 155\",\"children\":null,\"data\":null}, {\"id\":156,\"pid\":200,\"name\":\"Second-level node of special top-level node 200 156\",\"children\":null,\"data\":null}, {\"id\":157,\"pid\":200,\"name\":\"Second-level node of special top-level node 200 157\",\"children\":null,\"data\":null}, {\"id\":158,\"pid\":200,\"name\":\"Second-level node of special top-level node 200 158\",\"children\":null,\"data\":null}, {\"id\":159,\"pid\":200,\"name\":\"Second-level node of special top-level node 200 159\",\"children\":null,\"data\":null}],\"data\":null}, {\"id\":100,\"pid\":102,\"name\":\"Illegal node\",\"children\":null,\"data\":null}]";
        assertEquals(successResult, result);
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
     * 调用转换树型结构测试方法
     *
     * @param list 原始数据
     */
    private String performConversionTest(List<Demo> list, Map<String, Data> idData) {
        long startTime = System.currentTimeMillis();
        Predicate<String> isRootPredicate = parentId -> parentId.isEmpty() || "-1".equals(parentId);

        List<Demo> convertData = TreeListUtils.convertAndAddData(list, idData, "id", "pid", "children", "data", isRootPredicate);

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        log.info("method execution time: {} milliseconds; Top-level tree structure length: {}",
                elapsedTime, convertData.size());
        return convertData.toString();
    }


    public static class Data {
        private Integer id;
        private String other;

        public Data() {
        }

        public Data(Integer id, String other) {
            this.id = id;
            this.other = other;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getOther() {
            return other;
        }

        public void setOther(String other) {
            this.other = other;
        }

        @Override
        public String toString() {
            return "{\"id\":" + id + ",\"other\":\"" + other + "\"}";
        }
    }

    public static class Demo {
        private Integer id;
        private Integer pid;
        private String name;
        private List<Demo> children;

        private Data data;

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

        public Data getData() {
            return data;
        }

        public void setData(Data data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return "{\"id\":" + id + ",\"pid\":" + pid + ",\"name\":\"" + name + "\",\"children\":" + children + ",\"data\":" + data + "}";
        }

    }

}
