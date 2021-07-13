package com.dcat.ReCo.dtos;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

public interface VoucherDetail {
	
	Long getId();

	String getTitle();

	String getValue();

	String getCode();

	LocalDate getFromTime();

	LocalDate getToTime();
	
	String getContent();
	
	Restaurant getRestaurant();

	interface Restaurant {
		Long getId();

		String getLogo();

		String getName();

		Integer getStarAverage();

		@Value("#{target.userLikes.size()}")
		Integer getCountUserLike();

		default Integer getCountReservation() {
			return 1;
		};

		RestaurantAddress getAddress();

		String getSuitable();

		List<RestaurantTag> getTags();

		interface RestaurantTag {
			String getName();
		}

		interface RestaurantAddress {
			String getDetail();
		}
	}
}
