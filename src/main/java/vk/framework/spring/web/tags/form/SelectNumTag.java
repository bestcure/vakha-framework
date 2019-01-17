package vk.framework.spring.web.tags.form;

import java.io.IOException;
import java.util.LinkedHashMap;
import javax.servlet.jsp.JspException;

import vk.framework.spring.util.StringUtil;
import vk.framework.spring.web.tags.form.SelectTag;

public class SelectNumTag extends SelectTag {
	private int startNum;
	private int endNum;
	private char lpad;
	private int padLength = 0;
	private int step = 1;

	public int getStartNum() {
		return this.startNum;
	}

	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}

	public void setEndNum(int endNum) {
		this.endNum = endNum;
	}

	public void setLpad(String lpad) {
		this.lpad = lpad.charAt(0);
	}

	public void setPadLength(int padLength) {
		this.padLength = padLength;
	}

	public int getStep() {
		return this.step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public void doTag() throws JspException, IOException {
		LinkedHashMap dataMap = new LinkedHashMap();
		String value = "";

		for (int i = this.startNum; i < this.endNum + 1; i += this.step) {
			if (this.padLength > 0) {
				value = StringUtil.lpad(String.valueOf(i), this.padLength, this.lpad);
			} else {
				value = String.valueOf(i);
			}

			dataMap.put(value, value);
		}

		super.setItems(dataMap);
		super.doTag();
	}
}