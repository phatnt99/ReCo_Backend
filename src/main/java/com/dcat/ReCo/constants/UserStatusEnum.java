package com.dcat.ReCo.constants;

public enum UserStatusEnum {
	WAITING(1),
	ACTIVE(2),
	BLOCKED(3);

	private final int value;

	private UserStatusEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static UserStatusEnum fromInt(Integer value) {
		switch (value) {
		case 1:
			return UserStatusEnum.WAITING;
		case 2:
			return UserStatusEnum.ACTIVE;
		case 3:
			return UserStatusEnum.BLOCKED;
		default:
			return null;
		}
	}
}
