package com.dcat.ReCo.dtos;

public class ReportCreateDTO {
	private String content;
	private Long reportableId;
	private Integer type;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getReportableId() {
		return reportableId;
	}

	public void setReportableId(Long reportableId) {
		this.reportableId = reportableId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

}
