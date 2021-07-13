package com.dcat.ReCo.constants;

public enum ApproveStatusEnum {
	WAITING(1), ACCEPT(2), BLOCK(3);

	private final int value;

	private ApproveStatusEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	static public ApproveStatusEnum getEnum(Integer val) {

		switch (val) {
		case 1:
			return ApproveStatusEnum.WAITING;

		case 2:
			return ApproveStatusEnum.ACCEPT;

		case 3:
			return ApproveStatusEnum.BLOCK;

		default:
			return null;
		}
	}
}
