package cn.iosd.starter.datasource.mybatis.handler;


import cn.iosd.starter.web.utils.JsonUtil;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

/**
 * MyBatis 自定义类型处理器，将JsonNode类型映射为JSON字符串类型
 *
 * @author ok1996
 */
public class JacksonTypeHandler extends BaseTypeHandler<JsonNode> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, JsonNode parameter, JdbcType jdbcType) throws SQLException {
        String json = parameter.toString();
        ps.setString(i, json);
    }

    @Override
    public JsonNode getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String json = rs.getString(columnName);
        return getJsonNode(json);
    }

    @Override
    public JsonNode getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        return getJsonNode(json);
    }

    @Override
    public JsonNode getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        return getJsonNode(json);
    }

    private JsonNode getJsonNode(String json) {
        if (StringUtils.isEmpty(json)){
            return null;
        }
        try {
            Optional<JsonNode> jsonNode = Optional.ofNullable(JsonUtil.getObjectMapper().readTree(json));
            return jsonNode.orElse(null);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }
}
