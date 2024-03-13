package cn.iosd.starter.datasource.type;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.io.ByteOrderValues;
import org.locationtech.jts.io.ParseException;
import org.locationtech.jts.io.WKBReader;
import org.locationtech.jts.io.WKBWriter;
import org.locationtech.jts.io.WKTReader;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 几何类型处理器
 * <p>
 * 将数据库中的几何数据表示为字符串，将字符串转换为几何数据
 * </p>
 * 注：该处理器在处理几何数据时假定数据库中的数据以WKB格式存储
 *
 * @author ok1996
 */
@MappedJdbcTypes(JdbcType.JAVA_OBJECT)
@MappedTypes({String.class})
public class GeometryTypeHandler extends BaseTypeHandler<String> {
    /**
     * 将WKT格式的字符串转换为几何对象
     */
    private final WKTReader wktReader = new WKTReader(new GeometryFactory(new PrecisionModel()));
    /**
     * 将几何对象转换为WKB格式的字节数组
     */
    private final WKBWriter wkbWriter = new WKBWriter(2, ByteOrderValues.LITTLE_ENDIAN, false);

    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, String s, JdbcType jdbcType) throws SQLException {
        try {
            Geometry geo = wktReader.read(s);
            byte[] geometryBytes = wkbWriter.write(geo);
            byte[] wkb = new byte[geometryBytes.length + 4];
            wkb[0] = wkb[1] = wkb[2] = wkb[3] = 0;
            System.arraycopy(geometryBytes, 0, wkb, 4, geometryBytes.length);
            preparedStatement.setBytes(i, wkb);
        } catch (ParseException e) {
            throw new SQLException("Failed to parse geometry", e);
        }
    }

    @Override
    public String getNullableResult(ResultSet resultSet, String s) throws SQLException {
        try (InputStream inputStream = resultSet.getBinaryStream(s)) {
            return inputStream == null
                    ? null
                    : getGeometryAsString(inputStream);
        } catch (Exception e) {
            throw new SQLException("Failed to get geometry", e);
        }
    }

    @Override
    public String getNullableResult(ResultSet resultSet, int i) throws SQLException {
        try (InputStream inputStream = resultSet.getBinaryStream(i)) {
            return inputStream == null
                    ? null
                    : getGeometryAsString(inputStream);
        } catch (Exception e) {
            throw new SQLException("Failed to get geometry", e);
        }
    }

    @Override
    public String getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        try {
            byte[] bytes = callableStatement.getBytes(i);
            return bytes == null
                    ? null
                    : getGeometryAsString(new ByteArrayInputStream(bytes));
        } catch (Exception e) {
            throw new SQLException("Failed to get geometry", e);
        }
    }

    private String getGeometryAsString(InputStream inputStream) throws Exception {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            ba.write(buffer, 0, bytesRead);
        }
        byte[] geometryAsBytes = ba.toByteArray();
        if (geometryAsBytes.length < 5) {
            throw new RuntimeException("Failed to get geometry: Invalid geometry");
        }

        byte[] srIdBytes = new byte[4];
        System.arraycopy(geometryAsBytes, 0, srIdBytes, 0, 4);
        int srId = ByteOrderValues.getInt(geometryAsBytes, 0);

        WKBReader wkbReader = new WKBReader();
        byte[] wkb = new byte[geometryAsBytes.length - 4];
        System.arraycopy(geometryAsBytes, 4, wkb, 0, wkb.length);
        Geometry dbGeometry = wkbReader.read(wkb);
        dbGeometry.setSRID(srId);

        return dbGeometry.toString();
    }
}
