package vk.framework.spring.mapper.mybatis;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import vk.framework.spring.vo.PaginationVo;

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PaginationInterceptor implements Interceptor {
	private static final String SQL_SELECT_REGEX = "(?is)^\\s*SELECT.*$";
	private static final String SQL_COUNT_REGEX = "(?is)^\\s*SELECT\\s+COUNT.*$";
	private String MAPPER_PATTERN = "Paging";

	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		Object delegate = FieldUtils.readField(statementHandler, "delegate", true);
		Configuration configuration = (Configuration) FieldUtils.readField(delegate, "configuration", true);
		BoundSql boundSql = statementHandler.getBoundSql();
		String sql = boundSql.getSql();
		if (StringUtils.isEmpty(sql)) {
			return invocation.proceed();
		} else {
			if (sql.matches(this.MAPPER_PATTERN)) {
				;
			}

			if (sql.matches("(?is)^\\s*SELECT.*$") && !Pattern.matches("(?is)^\\s*SELECT\\s+COUNT.*$", sql)) {
				Object param = boundSql.getParameterObject();
				if (param == null) {
					return invocation.proceed();
				}

				PaginationVo paginationVo = new PaginationVo();
				BeanUtils.copyProperties(param, paginationVo);
				if (!paginationVo.isPagination()) {
					return invocation.proceed();
				}

				if (paginationVo != null && paginationVo.getLastIndex().intValue() > 0) {
					FieldUtils.writeField(boundSql, "sql",
							this.convertPagingSql(statementHandler, configuration, paginationVo), true);
				}
			}

			return invocation.proceed();
		}
	}

	public String convertPagingSql(StatementHandler statementHandler, Configuration configuration,
			PaginationVo paginationVo) {
		String startSql = "";
		String endSql = "";
		if (configuration.getDatabaseId().equalsIgnoreCase("MySQL")) {
			endSql = "LIMIT " + paginationVo.getFirstIndex() + ", " + paginationVo.getLastIndex();
			return statementHandler.getBoundSql().getSql() + " " + endSql;
		} else if (configuration.getDatabaseId().equalsIgnoreCase("MSSQL")) {
			return statementHandler.getBoundSql().getSql() + endSql;
		} else if (configuration.getDatabaseId().equalsIgnoreCase("Oracle")) {
			startSql = " SELECT * ";
			startSql = startSql + "FROM   (";
			startSql = startSql + "       SELECT  row_.*, ROWNUM rownum_ ";
			startSql = startSql + "       FROM    ( ";
			endSql = "                  ) row_ ";
			endSql = endSql + "         WHERE   ROWNUM <= "
					+ paginationVo.getPageUnit().intValue() * paginationVo.getPageIndex().intValue();
			endSql = endSql + "        ) ";
			endSql = endSql + "  WHERE   rownum_ > " + paginationVo.getFirstIndex();
			return startSql + " " + statementHandler.getBoundSql().getSql() + " " + endSql;
		} else {
			return statementHandler.getBoundSql().getSql();
		}
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties arg0) {
		String mapperPattern = arg0.getProperty("mapperPattern");
		if (!StringUtils.isEmpty(mapperPattern)) {
			this.MAPPER_PATTERN = mapperPattern;
		}

	}

	public static void main(String[] args) {
		String SQL_SELECT_REGEX = "^\\s*SELECT.*$";
		String SQL_COUNT_REGEX = "^\\s*SELECT\\s+COUNT\\s*\\(\\s*(?:\\*|\\w+)\\s*\\).*$";
		ArrayList tests = new ArrayList();
		tests.add("select count(*) from abc \n\t\t where\n abc");
		tests.add("SELECT   COUNT(*) from abc");
		tests.add(" select count  (*) from abc");
		tests.add("  select count(  *) from abc");
		tests.add("select count( *  ),id   from abc");
		tests.add("select * from abc");
		tests.add("select abc,test,fdas from abc");
		tests.add("SELECT COUNT(ADB) FROM ABC");
		tests.add("select count(0) from abc");
		tests.add("select min(count(*)) from abc");
		tests.add("update min(count(*)) from abc");
		tests.add("delete min(count(*)) from abc");
		Pattern p1 = Pattern.compile(SQL_SELECT_REGEX, 34);
		Pattern p2 = Pattern.compile(SQL_COUNT_REGEX, 34);
		Iterator arg5 = tests.iterator();

		while (arg5.hasNext()) {
			String str = (String) arg5.next();
			Matcher m1 = p1.matcher(str);
			Matcher m2 = p2.matcher(str);
			System.out.println("query : " + str);
			System.out.println("    select :  " + m1.matches());
			System.out.println("    count :  " + m2.matches());
			System.out.println("    count :  " + Pattern.matches(SQL_COUNT_REGEX, str));
			System.out.println();
		}

	}
}