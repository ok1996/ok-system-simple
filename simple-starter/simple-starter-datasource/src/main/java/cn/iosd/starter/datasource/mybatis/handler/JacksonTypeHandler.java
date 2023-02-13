package cn.iosd.starter.datasource.mybatis.handler;


import cn.iosd.starter.web.utils.JsonUtil;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
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
        if (json != null) {
            return read(json);
        } else {
            return null;
        }
    }

    @Override
    public JsonNode getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String json = rs.getString(columnIndex);
        if (json != null) {
            return read(json);
        } else {
            return null;
        }
    }

    @Override
    public JsonNode getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String json = cs.getString(columnIndex);
        if (json != null) {
            return read(json);
        } else {
            return null;
        }
    }

    private JsonNode read(String json) {
        try {
            return JsonUtil.DEFAULT_INSTANCE.readTree(json);
        } catch (JsonParseException e) {
            return null;
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }
}