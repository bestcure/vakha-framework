package vk.framework.spring.task.vo;

import java.util.Date;

import vk.framework.spring.vo.BaseVo;

public class GlobalTaskVo extends BaseVo {
	private String taskId;
	private String taskCd;
	private String taskNm;
	private Long taskNo;
	private Date startDt;
	private Date endDt;
	private String execCd;
	private String completeYn;
	private Integer completeCnt;
	private Integer failCnt;
	private String message;
	private String cronExpression;

	public String getTaskId() {
		return this.taskId;
	}

	public String getTaskCd() {
		return this.taskCd;
	}

	public String getTaskNm() {
		return this.taskNm;
	}

	public Long getTaskNo() {
		return this.taskNo;
	}

	public Date getStartDt() {
		return this.startDt;
	}

	public Date getEndDt() {
		return this.endDt;
	}

	public String getExecCd() {
		return this.execCd;
	}

	public String getCompleteYn() {
		return this.completeYn;
	}

	public Integer getCompleteCnt() {
		return this.completeCnt;
	}

	public Integer getFailCnt() {
		return this.failCnt;
	}

	public String getMessage() {
		return this.message;
	}

	public String getCronExpression() {
		return this.cronExpression;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setTaskCd(String taskCd) {
		this.taskCd = taskCd;
	}

	public void setTaskNm(String taskNm) {
		this.taskNm = taskNm;
	}

	public void setTaskNo(Long taskNo) {
		this.taskNo = taskNo;
	}

	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	public void setExecCd(String execCd) {
		this.execCd = execCd;
	}

	public void setCompleteYn(String completeYn) {
		this.completeYn = completeYn;
	}

	public void setCompleteCnt(Integer completeCnt) {
		this.completeCnt = completeCnt;
	}

	public void setFailCnt(Integer failCnt) {
		this.failCnt = failCnt;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}
}