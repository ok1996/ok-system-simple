package cn.iosd.demo.datasource.domain;

import cn.iosd.starter.datasource.type.GeometryTypeHandler;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * datasource示例模块数据库对象 demo_source
 *
 * @author ok1996
 * @date 2022-12-14
 */
@TableName(autoResultMap = true)
public class DemoDatasource implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 内容
     */
    private String content;

    /**
     * 几何数据
     */
    @TableField(typeHandler = GeometryTypeHandler.class)
    private String geom;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getGeom() {
        return geom;
    }

    public void setGeom(String geom) {
        this.geom = geom;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("content", getContent())
                .append("geom", getGeom())
                .toString();
    }
}
