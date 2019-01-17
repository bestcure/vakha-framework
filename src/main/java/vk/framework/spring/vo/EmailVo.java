package vk.framework.spring.vo;

public class EmailVo {
	private String subject;
	private String body;
	private String from;
	private String fromNm;
	private String to;
	private String cc;
	private String bcc;
	private Integer seqno;

	public String getSubject() {
		return this.subject;
	}

	public String getBody() {
		return this.body;
	}

	public String getFrom() {
		return this.from;
	}

	public String getFromNm() {
		return this.fromNm;
	}

	public String getTo() {
		return this.to;
	}

	public String getCc() {
		return this.cc;
	}

	public String getBcc() {
		return this.bcc;
	}

	public Integer getSeqno() {
		return this.seqno;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setFromNm(String fromNm) {
		this.fromNm = fromNm;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public void setCc(String cc) {
		this.cc = cc;
	}

	public void setBcc(String bcc) {
		this.bcc = bcc;
	}

	public void setSeqno(Integer seqno) {
		this.seqno = seqno;
	}
}