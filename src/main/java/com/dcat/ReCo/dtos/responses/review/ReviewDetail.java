package com.dcat.ReCo.dtos.responses.review;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.dcat.ReCo.models.Tag;

public interface ReviewDetail {
	Long getId();

	String getListPhoto();

	String getTitle();

	Double getPoint();

	String getContent();

	@Value("#{target.userLikes.size()}")
	Integer getCountUserLike();

	List<Tag> getTags();

	ReviewUser getUser();

	ReviewRestaurant getRestaurant();
	
	Date getCreatedAt();

	interface ReviewUser {
		Long getId();

		String getFullName();

		String getAvatar();
	}

	interface ReviewRestaurant {
		Long getId();

		String getLogo();

		String getName();

		Double getStarAverage();

		@Value("#{target.userLikes.size()}")
		Integer getCountUserLike();

		default Integer getCountReservation() {
			return 1;
		};

		ReviewRestaurantAddress getAddress();

		String getSuitable();

		List<ReviewRestaurantTag> getTags();

		interface ReviewRestaurantTag {
			String getName();
		}

		interface ReviewRestaurantAddress {
			String getDetail();
		}
	}

}
