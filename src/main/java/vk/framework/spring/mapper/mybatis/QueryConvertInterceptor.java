package vk.framework.spring.mapper.mybatis;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ResultMap;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.Configuration;
import org.springframework.beans.BeanUtils;

import vk.framework.spring.vo.PaginationVo;

@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class QueryConvertInterceptor implements Interceptor {
	private String mapperPattern = "select\\*List|select\\*Paging\\*";

	public Object intercept(Invocation invocation) throws Throwable {
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		Object delegate = FieldUtils.readField(statementHandler, "delegate", true);
		MappedStatement mappedStatement = (MappedStatement) FieldUtils.readField(delegate, "mappedStatement", true);
		Configuration configuration = (Configuration) FieldUtils.readField(delegate, "configuration", true);
		BoundSql boundSql = statementHandler.getBoundSql();
		if (StringUtils.isEmpty(boundSql.getSql())) {
			return invocation.proceed();
		} else {
			try {
				FieldUtils.writeField(boundSql, "sql", this.convertSql(boundSql, configuration, mappedStatement), true);
			} catch (Exception arg7) {
				System.err.println(arg7.getMessage());
			}

			return invocation.proceed();
		}
	}

	public String convertSql(BoundSql boundSql, Configuration configuration, MappedStatement mappedStatement)
			throws Exception {
		String sql = boundSql.getSql();
		String databaseId = StringUtils.defaultString(configuration.getDatabaseId(), "ORACLE");
		if (databaseId.equalsIgnoreCase("MYSQL")) {
			sql = sql.replaceAll("(?i)GETDATE([(].*?[)])", "NOW$1");
			sql = sql.replaceAll("(?i)SYSDATE", "NOW()");
			sql = sql.replaceAll("(?i)ISNULL([(].*?[)])", "IFNULL$1");
			sql = sql.replaceAll("(?i)NVL([(].*?[)])", "IFNULL$1");
			sql = sql.replaceAll("(?i)LEN([(].*?[)])", "LENGTH$1(");
			sql = sql.replaceAll("(?i)SUBSTR([(].*?[)])", "SUBSTRING$1");
			sql = sql.replaceAll("(?i)DBO.", "");
		} else if (!databaseId.equalsIgnoreCase("MSSQL") && !databaseId.equalsIgnoreCase("SQL Server")
				&& !databaseId.equalsIgnoreCase("Microsoft SQL Server")) {
			if (databaseId.equalsIgnoreCase("ORACLE")) {
				sql = sql.replaceAll("(?i)NOW([(].*?[)])", "SYSDATE");
				sql = sql.replaceAll("(?i)IFNULL([(].*?[)])", "NVL$1");
				sql = sql.replaceAll("(?i)ISNULL([(].*?[)])", "NVL$1(");
				sql = sql.replaceAll("(?i)SUBSTRING([(].*?[)])", "SUBSTR$1");
				sql = sql.replaceAll("(?i)DBO.", "");
				sql = this.replaceConcat(sql, "||");
				if (sql.contains("SELECT") && !sql.contains("FROM") && !sql.contains("from") && !sql.contains("dual")
						&& !sql.contains("DUAL")) {
					sql = sql + "FROM DUAL ";
				}
			}
		} else {
			sql = this.replaceConcat(sql, "+");
			sql = sql.replaceAll("(?i)NOW([(].*?[)])", "GETDATE$1");
			sql = sql.replaceAll("(?i)SYSDATE", "GETDATE()");
			sql = sql.replaceAll("(?i)IFNULL([(].*?[)])", "ISNULL$1");
			sql = sql.replaceAll("(?i)NVL([(].*?[)])", "ISNULL$1");
			sql = sql.replaceAll("(?i)LENGTH([(].*?[)])", "LEN$1(");
			sql = sql.replaceAll("(?i)SUBSTR([(].*?[)])", "SUBSTRING$1");
			sql = sql.replaceAll("(?i)DATE_FORMAT([(].*?[)])", "DBO.DATE_FORMAT$1");
			sql = sql.replaceAll("(?i)UF_", "dbo.UF_");
		}

		if (mappedStatement.getResultMaps().size() > 0
				&& !((ResultMap) mappedStatement.getResultMaps().get(0)).getType().getName().contains("DataMap")) {
			return sql;
		} else {
			this.mapperPattern = "\\s*selectList|\\s*select.*List|select.*Paging*";
			Pattern pattern = Pattern.compile(this.mapperPattern);
			if (pattern.matcher(mappedStatement.getId()).find()) {
				Object param = boundSql.getParameterObject();
				PaginationVo paginationVo = new PaginationVo();
				if (param != null) {
					BeanUtils.copyProperties(param, paginationVo);
				}

				String startSql = "";
				String endSql = "";
				if (paginationVo.isPagination()) {
					if (databaseId.equalsIgnoreCase("MYSQL")) {
						if (!sql.contains("LIMIT")) {
							endSql = "LIMIT " + paginationVo.getFirstIndex() + ", " + paginationVo.getLastIndex();
							sql = sql + " " + endSql;
						}
					} else if (databaseId.equalsIgnoreCase("MSSQL")) {
						if (!sql.contains("OFFSET")) {
							endSql = endSql + " OFFSET " + paginationVo.getFirstIndex() + "ROWS ";
							endSql = endSql + " FETCH NEXT " + paginationVo.getLastIndex() + " ROWS ONLY ";
							sql = startSql + " " + sql + " " + endSql;
						}
					} else if (databaseId.equalsIgnoreCase("ORACLE") && !sql.contains("ROWNUM")) {
						startSql = " SELECT * ";
						startSql = startSql + "FROM   (";
						startSql = startSql + "       SELECT  row_.*, ROWNUM rownum_ ";
						startSql = startSql + "       FROM    ( ";
						endSql = "                  ) row_ ";
						endSql = endSql + "         WHERE   ROWNUM <= "
								+ paginationVo.getPageUnit().intValue() * paginationVo.getPageIndex().intValue();
						endSql = endSql + "        ) ";
						endSql = endSql + "  WHERE   rownum_ > " + paginationVo.getFirstIndex();
						sql = startSql + " " + sql + " " + endSql;
					}
				}
			}

			return sql;
		}
	}

	public String replaceConcat(String sql, String concatStr) {
		Pattern patternConcat = Pattern.compile("CONCAT\\([^()]|(R)*\\)", 74);
		patternConcat.matcher(sql);
		patternConcat = Pattern.compile("CONCAT[(]+.*[)]", 74);

		String concat;
		String argument;
		for (Matcher matcherConcat = patternConcat.matcher(sql); matcherConcat
				.find(); sql = sql.replace(concat, argument)) {
			concat = matcherConcat.group();
			argument = matcherConcat.group(0).replace("CONCAT", "").replace("concat", "");
			argument = argument.replaceAll(",(?=(?:[^\']*\'[^\']*\')*[^\']*$)", " " + concatStr);
		}

		return sql;
	}

	private String replaceQuery(String query, String regex, String replacement) {
		Pattern pattern = Pattern.compile(regex, 10);
		Matcher matcher = pattern.matcher(query);
		if (matcher.find()) {
			System.out.println(pattern.matcher(query));
			System.out.println(matcher.group(1));
			return pattern.matcher(query).replaceAll(replacement);
		} else {
			return query;
		}
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties arg0) {
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