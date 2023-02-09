package cn.iosd.base.param.vo;

/**
 * @author ok1996
 */
public class BaseParamCodeValueVo<V> {
    private String code;
    private V value;

    public String getCode() {
        return this.code;
    }

    public V getValue() {
        return this.value;
    }

    public BaseParamCodeValueVo<V> setCode(final String code) {
        this.code = code;
        return this;
    }

    public BaseParamCodeValueVo<V> setValue(final V value) {
        this.value = value;
        return this;
    }

    public String toString() {
        return "BaseParamCodeValueVo(code=" + this.getCode() + ", value=" + this.getValue() + ")";
    }
}
