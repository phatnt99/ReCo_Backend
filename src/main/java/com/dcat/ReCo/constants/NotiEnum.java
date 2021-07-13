package com.dcat.ReCo.constants;

public enum NotiEnum {
	VOUCHER(1),
	RESTAURANT(2),
	RESERVATION(3),
	OTHER(4);

	private final int value;

	private NotiEnum(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public static NotiEnum fromInt(Integer value) {
		switch (value) {
		case 1:
			return NotiEnum.VOUCHER;
		case 2:
			return NotiEnum.RESTAURANT;
		case 3:
			return NotiEnum.RESERVATION;
		default:
			return NotiEnum.OTHER;
		}
	}
}
