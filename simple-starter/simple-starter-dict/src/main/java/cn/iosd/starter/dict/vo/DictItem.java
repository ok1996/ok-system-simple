package cn.iosd.starter.dict.vo;

/**
 * @author ok1996
 */
public class DictItem {
    /**
     * 字典项值
     */
    private String value;
    /**
     * 字典项描述
     */
    private String label;

    public DictItem() {
    }

    public DictItem(String value, String label) {
        this.value = value;
        this.label = label;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
