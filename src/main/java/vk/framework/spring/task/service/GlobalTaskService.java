package vk.framework.spring.task.service;

import java.util.Map;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Service;

import vk.framework.spring.task.vo.GlobalTaskVo;

@Service
public class GlobalTaskService extends JdbcDaoSupport {
	@Autowired
	protected DataSource dataSource;
	protected JdbcTemplate jdbcTemplate;
	@Autowired
	protected HttpServletRequest request;

	@PostConstruct
	private void initialize() {
		this.jdbcTemplate = new JdbcTemplate(this.dataSource);
		this.setDataSource(this.dataSource);
	}

	public Map<String, Object> select(String taskId) throws Exception {
		return this.select(taskId, Integer.valueOf(0));
	}

	public Map<String, Object> select(String taskId, Integer taskNo) {
		StringBuffer query = new StringBuffer();
		query.append("SELECT task_id              ");
		query.append("       ,start_dt               ");
		query.append("       ,end_dt                 ");
		query.append("       ,exec_cd                ");
		query.append("       ,exec_cnt               ");
		query.append("FROM   tb_sys_task                          ");
		query.append("WHERE  task_id    = ?                       ");
		Map resultMap = this.jdbcTemplate.queryForMap(query.toString(), new Object[]{taskId});
		if (resultMap != null) {
			query = new StringBuffer();
			query.append("SELECT task_id              ");
			query.append("       ,start_dt               ");
			query.append("       ,end_dt                 ");
			query.append("       ,exec_cd                ");
			query.append("FROM   tb_sys_task_log                      ");
			query.append("WHERE  task_id    = ?                       ");
			query.append("AND    task_no    = ?                       ");
		}

		return this.jdbcTemplate.queryForMap(query.toString(), new Object[]{taskId, taskNo});
	}

	public Long selectLog(String taskId) {
		StringBuffer query = new StringBuffer();
		query.append("SELECT IFNULL(MAX(task_no), 0) as task_no       ");
		query.append("FROM   tb_sys_task_log                          ");
		query.append("WHERE  task_id    = ?                           ");
		return (Long) this.jdbcTemplate.queryForObject(query.toString(), new Object[]{taskId}, Long.class);
	}

	public String selectCode(String taskId) {
		StringBuffer query = new StringBuffer();
		query.append("SELECT code_nm                              ");
		query.append("FROM   tb_sys_code                          ");
		query.append("WHERE  code       = ?                       ");
		query.append("AND    group_cd   = \'ASYNC\'                 ");

		String codeName;
		try {
			codeName = (String) this.jdbcTemplate.queryForObject(query.toString(), new Object[]{taskId}, String.class);
		} catch (Exception arg4) {
			codeName = taskId;
		}

		return codeName;
	}

	public void insert(GlobalTaskVo taskVo) throws Exception {
		String regId = "SYSTEM";
		String regIp = "0.0.0.0";

		try {
			regIp = this.request.getHeader("X-FORWARDED-FOR");
			if (regIp == null) {
				regIp = this.request.getRemoteAddr();
			}
		} catch (Exception arg6) {
			this.logger.debug(arg6.getMessage());
		}

		StringBuffer query = new StringBuffer();
		String databaseName = (String) JdbcUtils.extractDatabaseMetaData(this.jdbcTemplate.getDataSource(),
				"getDatabaseProductName");
		if (databaseName.equalsIgnoreCase("ORACLE")) {
			query.append(
					"INSERT INTO tb_sys_task                                                                                             ");
			query.append("  USING DUAL ON ( task_id = \'" + taskVo.getTaskId()
					+ "\' )                                                            ");
			query.append(
					"  WHEN NOT MATCHED THEN                                                                                             ");
			query.append(
					"  INSERT (                                                                                                          ");
			query.append(
					"      task_id                                                                                        ");
			query.append(
					"      ,task_cd                                                                                        ");
			query.append(
					"      ,start_dt                                                                                         ");
			query.append(
					"      ,exec_cd                                                                                          ");
			query.append(
					"      ,exec_cnt                                                                                         ");
			query.append(
					"      ,cron_expression                                                                                ");
			query.append(
					"      ,reg_id                                                                                         ");
			query.append(
					"      ,reg_dt                                                                                           ");
			query.append(
					"      ,reg_ip                                                                                          ");
			query.append(
					"  ) VALUES (                                                                                                         ");
			query.append(
					"      ?                                                                                                              ");
			query.append(
					"      ,?                                                                                                             ");
			query.append(
					"      ,SYSDATE                                                                                                       ");
			query.append(
					"      ,?                                                                                                             ");
			query.append(
					"      ,0                                                                                                             ");
			query.append(
					"      ,?                                                                                                             ");
			query.append(
					"      ,?                                                                                                             ");
			query.append(
					"      ,SYSDATE                                                                                                       ");
			query.append(
					"      ,?                                                                                                             ");
			query.append(
					"  )                                                                                                                  ");
			query.append(
					"  WHEN MATCHED THEN                                                                                                  ");
			query.append(
					"  UPDATE SET                                                                                                         ");
			query.append(
					"      start_dt        = CASE WHEN ? = \'START\' THEN SYSDATE ELSE start_dt END                                         ");
			query.append(
					"      ,end_dt         = CASE WHEN ? != \'START\' THEN SYSDATE ELSE null END                                            ");
			query.append(
					"      ,exec_cd        = ?                                                                               ");
			query.append(
					"      ,exec_cnt       = IFNULL(exec_cnt, 0)  + 1                                                        ");
			query.append(
					"      ,fail_cnt       = CASE WHEN ? = \'FAIL\' THEN IFNULL(fail_cnt, 0)  + 1 ELSE fail_cnt END                         ");
			query.append(
					"      ,cron_expression= ?                                                                                            ");
			query.append(
					"      ,upd_id         = ?                                                                                ");
			query.append(
					"      ,upd_dt         = SYSDATE                                                                          ");
			query.append(
					"      ,upd_ip         = ?                                                                                ");
		} else if (databaseName.equalsIgnoreCase("MSSQL")) {
			query.append(
					"INSERT INTO tb_sys_task                                                                                             ");
			query.append("  USING (SELECT 1) ON ( task_id = \'" + taskVo.getTaskId()
					+ "\' )                                                      ");
			query.append(
					"  WHEN NOT MATCHED THEN                                                                                             ");
			query.append(
					"  INSERT (                                                                                                          ");
			query.append(
					"      task_id                                                                                        ");
			query.append(
					"      ,task_cd                                                                                        ");
			query.append(
					"      ,start_dt                                                                                         ");
			query.append(
					"      ,exec_cd                                                                                          ");
			query.append(
					"      ,exec_cnt                                                                                         ");
			query.append(
					"      ,cron_expression                                                                                ");
			query.append(
					"      ,reg_id                                                                                         ");
			query.append(
					"      ,reg_dt                                                                                           ");
			query.append(
					"      ,reg_ip                                                                                          ");
			query.append(
					"  ) VALUES (                                                                                                         ");
			query.append(
					"      ?                                                                                                              ");
			query.append(
					"      ,?                                                                                                             ");
			query.append(
					"      ,GETDATE()                                                                                                     ");
			query.append(
					"      ,?                                                                                                             ");
			query.append(
					"      ,0                                                                                                             ");
			query.append(
					"      ,?                                                                                                             ");
			query.append(
					"      ,?                                                                                                             ");
			query.append(
					"      ,GETDATE()                                                                                                     ");
			query.append(
					"      ,?                                                                                                             ");
			query.append(
					"  )                                                                                                                  ");
			query.append(
					"  WHEN MATCHED THEN                                                                                                  ");
			query.append(
					"  UPDATE SET                                                                                                         ");
			query.append(
					"      start_dt        = CASE WHEN ? = \'START\' THEN GETDATE() ELSE start_dt END                                       ");
			query.append(
					"      ,end_dt         = CASE WHEN ? != \'START\' THEN GETDATE() ELSE null END                                          ");
			query.append(
					"      ,exec_cd        = ?                                                                               ");
			query.append(
					"      ,exec_cnt       = IFNULL(exec_cnt, 0)  + 1                                                        ");
			query.append(
					"      ,fail_cnt       = CASE WHEN ? = \'FAIL\' THEN IFNULL(fail_cnt, 0)  + 1 ELSE fail_cnt END                         ");
			query.append(
					"      ,cron_expression= ?                                                                                            ");
			query.append(
					"      ,upd_id         = ?                                                                                ");
			query.append(
					"      ,upd_dt         = GETDATE()                                                                        ");
			query.append(
					"      ,upd_ip         = ?                                                                                ");
		} else {
			query.append(
					"INSERT INTO tb_sys_task (                                                                                           ");
			query.append(
					"      task_id                                                                                        ");
			query.append(
					"      ,task_cd                                                                                        ");
			query.append(
					"      ,start_dt                                                                                         ");
			query.append(
					"      ,exec_cd                                                                                          ");
			query.append(
					"      ,exec_cnt                                                                                         ");
			query.append(
					"      ,cron_expression                                                                                 ");
			query.append(
					"      ,reg_id                                                                                         ");
			query.append(
					"      ,reg_dt                                                                                           ");
			query.append(
					"      ,reg_ip                                                                                          ");
			query.append(
					"  ) VALUES (                                                                                                         ");
			query.append(
					"      ?                                                                                                              ");
			query.append(
					"      ,?                                                                                                             ");
			query.append(
					"      ,NOW()                                                                                                         ");
			query.append(
					"      ,?                                                                                                             ");
			query.append(
					"      ,0                                                                                                             ");
			query.append(
					"      ,?                                                                                                             ");
			query.append(
					"      ,?                                                                                                             ");
			query.append(
					"      ,NOW()                                                                                                         ");
			query.append(
					"      ,?                                                                                                             ");
			query.append(
					"  )                                                                                                                  ");
			query.append(
					"  ON DUPLICATE KEY UPDATE                                                                                            ");
			query.append(
					"      start_dt        = CASE WHEN ? = \'START\' THEN NOW() ELSE start_dt END                                           ");
			query.append(
					"      ,end_dt         = CASE WHEN ? != \'START\' THEN NOW() ELSE null END                                              ");
			query.append(
					"      ,exec_cd        = ?                                                                               ");
			query.append(
					"      ,exec_cnt       = IFNULL(exec_cnt, 0)  + 1                                                        ");
			query.append(
					"      ,fail_cnt       = CASE WHEN ? = \'FAIL\' THEN IFNULL(fail_cnt, 0)  + 1 ELSE fail_cnt END                         ");
			query.append(
					"      ,cron_expression= ?                                                                                            ");
			query.append(
					"      ,upd_id         = ?                                                                                ");
			query.append(
					"      ,upd_dt         = NOW()                                                                           ");
			query.append(
					"      ,upd_ip         = ?                                                                                ");
		}

		Object[] params = new Object[]{taskVo.getTaskId(), taskVo.getTaskCd(), taskVo.getExecCd(),
				taskVo.getCronExpression(), regId, regIp, taskVo.getExecCd(), taskVo.getExecCd(), taskVo.getExecCd(),
				taskVo.getExecCd(), taskVo.getCronExpression(), regId, regIp};
		this.jdbcTemplate.update(query.toString(), params);
		this.insertLog(taskVo);
	}

	public void insertLog(GlobalTaskVo taskVo) throws Exception {
		String regId = "SYSTEM";
		String regIp = "0.0.0.0";

		try {
			regIp = this.request.getHeader("X-FORWARDED-FOR");
			if (regIp == null) {
				regIp = this.request.getRemoteAddr();
			}
		} catch (Exception arg9) {
			this.logger.debug(arg9.getMessage());
		}

		String databaseName = (String) JdbcUtils.extractDatabaseMetaData(this.jdbcTemplate.getDataSource(),
				"getDatabaseProductName");
		String dateFunction = "NOW()";
		if (databaseName.equalsIgnoreCase("ORACLE")) {
			dateFunction = "SYSDATE";
		}

		StringBuffer query = new StringBuffer();
		query.append(
				"INSERT INTO tb_sys_task_log (                                                                                        ");
		query.append(
				"      task_id                                                                                        ");
		query.append(
				"      ,task_no                                                                                        ");
		query.append(
				"      ,start_dt                                                                                         ");
		query.append(
				"      ,exec_cd                                                                                          ");
		query.append(
				"      ,message                                                                                            ");
		query.append(
				"      ,reg_id                                                                                         ");
		query.append(
				"      ,reg_dt                                                                                           ");
		query.append(
				"      ,reg_ip                                                                                          ");
		query.append(
				"  ) VALUES (                                                                                                         ");
		query.append(
				"      ?                                                                                                              ");
		query.append(
				"      ,?                                                                                                             ");
		query.append("      ," + dateFunction
				+ "                                                                                          ");
		query.append(
				"      ,?                                                                                                             ");
		query.append(
				"      ,?                                                                                                             ");
		query.append(
				"      ,?                                                                                                             ");
		query.append("      ," + dateFunction
				+ "                                                                                          ");
		query.append(
				"      ,?                                                                                                             ");
		query.append(
				"  )                                                                                                                  ");
		query.append(
				"  ON DUPLICATE KEY UPDATE                                                                                            ");
		query.append("      start_dt        = CASE WHEN ? = \'START\' THEN " + dateFunction
				+ " ELSE start_dt END                            ");
		query.append("      ,end_dt         = CASE WHEN ? != \'START\' THEN " + dateFunction
				+ " ELSE null END                               ");
		query.append(
				"      ,exec_cd        = ?                                                                               ");
		query.append(
				"      ,message        = ?                                                                                            ");
		query.append(
				"      ,upd_id         = ?                                                                                ");
		query.append("      ,upd_dt         = " + dateFunction
				+ "                                                             ");
		query.append(
				"      ,upd_ip         = ?                                                                                ");
		Object[] params = new Object[]{taskVo.getTaskId(), taskVo.getTaskNo(), taskVo.getExecCd(), taskVo.getMessage(),
				regId, regIp, taskVo.getExecCd(), taskVo.getExecCd(), taskVo.getExecCd(), taskVo.getMessage(), regId,
				regIp};

		try {
			this.jdbcTemplate.update(query.toString(), params);
		} catch (Exception arg8) {
			this.logger.debug(arg8.getMessage());
		}

	}
}