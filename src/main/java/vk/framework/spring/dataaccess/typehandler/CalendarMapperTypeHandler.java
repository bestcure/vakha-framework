package vk.framework.spring.dataaccess.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class CalendarMapperTypeHandler implements TypeHandler<Calendar> {
	public void setParameter(PreparedStatement ps, int i, Calendar parameter, JdbcType jdbcType) throws SQLException {
		if (parameter == null) {
			ps.setNull(i, 91);
		} else {
			ps.setTimestamp(i, new Timestamp(parameter.getTimeInMillis()));
		}

	}

	public Calendar getResult(ResultSet rs, String columnName) throws SQLException {
		Calendar cal = Calendar.getInstance();
		if (rs.getTimestamp(columnName) == null) {
			return null;
		} else {
			Timestamp ts = rs.getTimestamp(columnName);
			cal.setTime(ts);
			return cal;
		}
	}

	public Calendar getResult(ResultSet rs, int columnIndex) throws SQLException {
		Calendar cal = Calendar.getInstance();
		Timestamp ts = rs.getTimestamp(columnIndex);
		cal.setTime(ts);
		return cal;
	}

	public Calendar getResult(CallableStatement cs, int columnIndex) throws SQLException {
		return null;
	}
}