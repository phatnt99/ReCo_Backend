package com.dcat.ReCo.dtos.voucher;

import java.time.LocalDate;

public interface VoucherTop10 {
	Long getId();

	String getTitle();

	String getCode();
	
	String getImage();
	
	LocalDate getFromTime();
	
	LocalDate getToTime();
}
