package com.dcat.ReCo.dtos.dashboard;

public class DashboardDTO {

	public class TagCount {
		private String name;
		private Long   count;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Long getCount() {
			return count;
		}

		public void setCount(Long count) {
			this.count = count;
		}

	}

}
