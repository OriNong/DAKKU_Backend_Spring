package kr.re.kh.mapper.handler;

import kr.re.kh.model.FriendshipStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(FriendshipStatus.class)
@MappedJdbcTypes(JdbcType.VARCHAR)
public class FriendshipStatusTypeHandler extends BaseTypeHandler<FriendshipStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, FriendshipStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public FriendshipStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return value != null ? FriendshipStatus.valueOf(value.toUpperCase()) : null;
    }

    @Override
    public FriendshipStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value != null ? FriendshipStatus.valueOf(value.toUpperCase()) : null;
    }

    @Override
    public FriendshipStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value != null ? FriendshipStatus.valueOf(value.toUpperCase()) : null;
    }
}