package com.dcat.ReCo.dtos.voucher;

import java.time.LocalDate;

public interface VoucherList {
	Long getId();

	String getTitle();

	String getImage();

	String getCode();

	LocalDate getFromTime();

	LocalDate getToTime();

	VoucherRestaurant getRestaurant();

	interface VoucherRestaurant {
		Long getId();

		String getName();
	}
}
