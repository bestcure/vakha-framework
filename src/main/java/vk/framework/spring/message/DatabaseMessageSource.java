package vk.framework.spring.message;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractMessageSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;

public class DatabaseMessageSource extends AbstractMessageSource implements InitializingBean {
	protected Log log = LogFactory.getLog(this.getClass());
	protected Map<String, Map<String, MessageFormat>> messages;
	protected boolean isNewInsert = false;
	protected String tablePrefix = "tb_";
	@Autowired
	protected DataSource dataSource;
	protected JdbcTemplate jdbcTemplate;

	@PostConstruct
	private void initialize() {
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
		this.setDataSource(this.dataSource);
	}

	public void afterPropertiesSet() throws Exception {
		this.initializeMessages();
	}

	protected MessageFormat resolveCode(String code, Locale locale) {
		String langCd = locale.getLanguage();
		MessageFormat messageFormat = null;
		Object messageFormatMap = (Map) this.messages.get(langCd);
		if (messageFormatMap != null && ((Map) messageFormatMap).containsKey(code)) {
			messageFormat = (MessageFormat) ((Map) messageFormatMap).get(code);
		} else {
			if (this.isNewInsert) {
				this.log.debug("###### NEW resolveCode(" + code + ", " + locale + ")" + " - " + locale.getLanguage());
				this.insertMessage(langCd, code, code);
			}

			messageFormat = this.createMessageFormat(code, locale);
			if (messageFormatMap == null) {
				messageFormatMap = new HashMap();
			}

			((Map) messageFormatMap).put(code, messageFormat);
		}

		return messageFormat;
	}

	protected void initializeMessages() {
		String langCd = "";
		String keyCd = "";
		HashMap messageFormatMap = null;
		this.messages = new HashMap();
		Iterator arg3 = this.selectMessageList().iterator();

		while (arg3.hasNext()) {
			Map map = (Map) arg3.next();
			if (map.get("lang_cd") == null) {
				this.log.debug(map.get("LANG_CD"));
				this.log.debug(map.get("KEY_CD"));
			}

			langCd = (String) ((String) (map.get("lang_cd") == null ? map.get("LANG_CD") : map.get("lang_cd")));
			keyCd = (String) ((String) (map.get("key_cd") == null ? map.get("KEY_CD") : map.get("key_cd")));
			if (!this.messages.containsKey(langCd)) {
				messageFormatMap = new HashMap();
				this.messages.put(langCd, messageFormatMap);
			}

			Map messageFormatMap1 = (Map) this.messages.get(langCd);
			messageFormatMap1.put(keyCd, this.createMessageFormat((String) map.get("message"), new Locale(langCd)));
		}

	}

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	public void setNewInsert(boolean isNewInsert) {
		this.isNewInsert = isNewInsert;
	}

	public void setTablePrefix(String tablePrefix) {
		this.tablePrefix = tablePrefix;
	}

	private final List<Map<String, Object>> selectMessageList() {
		StringBuffer query = new StringBuffer();
		query.append(" SELECT  lang_cd        ");
		query.append("        ,label_cd       ");
		query.append("        ,key_cd         ");
		query.append("        ,message        ");
		query.append(" FROM   " + this.tablePrefix + "sys_language ");
		System.out.println(query);
		return this.jdbcTemplate.queryForList(query.toString());
	}

	private final void insertMessage(String langCd, String keyCd, String message) {
		try {
			String ex = (String) JdbcUtils.extractDatabaseMetaData(this.jdbcTemplate.getDataSource(),
					"getDatabaseProductName");
			String dateFunction = "NOW()";
			if (ex.equalsIgnoreCase("ORACLE")) {
				dateFunction = "SYSDATE";
			}

			StringBuffer query = new StringBuffer();
			query.append("INSERT INTO " + this.tablePrefix + "sys_language ( ");
			query.append("     lang_cd                  ");
			query.append("    ,label_cd                 ");
			query.append("    ,key_cd                   ");
			query.append("    ,message                  ");
			query.append("    ,use_yn                   ");
			query.append("    ,new_yn                   ");
			query.append("    ,reg_dt                   ");
			query.append(") VALUES (                    ");
			query.append("     ?                        ");
			query.append("    ,\'label\'                  ");
			query.append("    ,?                        ");
			query.append("    ,?                        ");
			query.append("    ,\'Y\'                      ");
			query.append("    ,\'Y\'                      ");
			query.append("    ," + dateFunction + "     ");
			query.append(")                             ");
			System.out.println(query);

			try {
				this.jdbcTemplate.update(query.toString(), new Object[]{langCd, keyCd, message});
			} catch (Exception arg7) {
				this.log.debug("insertMessage fail :" + arg7.getMessage());
			}
		} catch (Exception arg8) {
			this.log.debug("duplicate multi language" + langCd + "/" + message);
		}

	}
}