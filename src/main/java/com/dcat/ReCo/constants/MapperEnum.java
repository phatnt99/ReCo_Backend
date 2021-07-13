package com.dcat.ReCo.constants;

public enum MapperEnum {
	CREATE(1),
	UPDATE(2);
	
	private final int value;
	
	private MapperEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
