package au.edu.anu.orcid.metadatastores.misc;

import javax.xml.bind.annotation.XmlElement;

public class MSFORSubject {
	private String code;
	private String percentage;
	private String value;

	@XmlElement(name="code")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@XmlElement(name="percentage")
	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	@XmlElement(name="value")
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
