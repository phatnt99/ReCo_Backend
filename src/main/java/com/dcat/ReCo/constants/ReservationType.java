package com.dcat.ReCo.constants;

public enum ReservationType {
	WAITING(1),
	ACCEPT(2),
	DECLINE(3),
	HISTORY(4);

	private final int value;

	ReservationType(int value) {
		this.value = value;
	}

	public int getValue() {
		return this.value;
	}

	public static ReservationType fromInt(int value) {
		switch (value) {
		case 1:
			return ReservationType.WAITING;
		case 2:
			return ReservationType.ACCEPT;
		case 3:
			return ReservationType.DECLINE;
		case 4:
			return ReservationType.HISTORY;
		default:
			return null;
		}
	}
	
	public static String toStr(int value) {
		switch (value) {
		case 1:
			return "Đang chờ duyệt";
		case 2:
			return "Đạ xác nhận";
		case 3:
			return "Đã hủy";
		case 4:
			return "Thành công";
		default:
			return null;
		}
	}
}
