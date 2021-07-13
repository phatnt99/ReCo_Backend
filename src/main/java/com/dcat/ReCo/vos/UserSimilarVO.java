package com.dcat.ReCo.vos;

import java.util.List;

public class UserSimilarVO {
	private Long          userId;
	private List<OtherVO> others;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public List<OtherVO> getOthers() {
		return others;
	}

	public void setOthers(List<OtherVO> others) {
		this.others = others;
	}

	class OtherVO {
		private Long   userId;
		private Double distance;

		public OtherVO(Long userId, Double distance) {
			super();
			this.userId   = userId;
			this.distance = distance;
		}

	}
}
