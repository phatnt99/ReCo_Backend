package com.dcat.ReCo.dtos.voucher;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;

import com.dcat.ReCo.dtos.address.AddressBasic;

public interface VoucheDetail {
	Long getId();

	String getTitle();

	String getImage();

	String getCode();

	LocalDate getFromTime();

	LocalDate getToTime();
	
	@Value("#{target.reservations.size()}")
	Integer getCount();
	
	VoucherDetailRestaurant getRestaurant();

	interface VoucherDetailRestaurant {
		Long getId();

		String getName();

		String getLogo();

		Double getStarAverage();
		
		@Value("#{target.userLikes.size()}")
		Integer getUserLikeCount();
		
		@Value("#{target.legalReservations.size()}")
		Integer getReservationCount();
		
		String getCarousel();
		
		String getSuitable();
		
		AddressBasic getAddress();
	}

}
