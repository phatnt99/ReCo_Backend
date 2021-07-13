package com.dcat.ReCo.constants;

public enum GenderEnum {
	MALE(1),
	FEMALE(2),
	UNKNOWN(0);
	
	private final int value;
	
	private GenderEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
}
