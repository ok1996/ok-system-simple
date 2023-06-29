package cn.iosd.base.param.vo;

/**
 * @author ok1996
 */
public class CodeValue<V> {
    private String code;
    private V value;

    public String getCode() {
        return this.code;
    }

    public V getValue() {
        return this.value;
    }

    public CodeValue<V> setCode(final String code) {
        this.code = code;
        return this;
    }

    public CodeValue<V> setValue(final V value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "CodeValue(code=" + this.getCode() + ", value=" + this.getValue() + ")";
    }
}
