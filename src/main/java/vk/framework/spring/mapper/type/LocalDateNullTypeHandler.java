package vk.framework.spring.mapper.type;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import org.apache.ibatis.type.DateOnlyTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeException;

public class LocalDateNullTypeHandler extends DateOnlyTypeHandler {
	public void setParameter(PreparedStatement ps, int i, Date parameter, JdbcType jdbcType) throws SQLException {
		if (parameter == null) {
			if (jdbcType == null) {
				throw new TypeException(
						"JDBC requires that the JdbcType must be specified for all nullable parameters.");
			}

			try {
				ps.setNull(i, 91);
			} catch (SQLException arg5) {
				throw new TypeException("Error setting null for parameter #" + i + " with JdbcType " + jdbcType + " . "
						+ "Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. "
						+ "Cause: " + arg5, arg5);
			}
		} else {
			this.setNonNullParameter(ps, i, parameter, jdbcType);
		}

	}
}