package com.dcat.ReCo.constants;

public enum ToggleUpdateEnum {
	ACTIVE(1),
	INACTIVE(2);
	
	private final int value;
	
	private ToggleUpdateEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
	
	public static ToggleUpdateEnum getEnum(int value) {
		for(ToggleUpdateEnum roleE : ToggleUpdateEnum.values()) {
			if(value == roleE.value)
				return roleE;
		}
		
		return null;
	}
}
