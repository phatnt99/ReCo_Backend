package com.dcat.ReCo.dtos;

public class MonthCount {
	private String month;
	private Long count;

	public MonthCount(String month, Long count) {
		super();
		this.month = month;
		this.count = count;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public Long getCount() {
		return count;
	}

	public void setCount(Long count) {
		this.count = count;
	}

}
