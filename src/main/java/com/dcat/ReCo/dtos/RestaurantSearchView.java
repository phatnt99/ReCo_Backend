package com.dcat.ReCo.dtos;

import org.springframework.beans.factory.annotation.Value;

public interface RestaurantSearchView {
	 Long getId();
	 String getName();
	 String getLogo();
	 @Value("#{target.address.getDetail()}")
	 String getAddress();
	 @Value("#{target.owner.getFullName()}")
	 String getOwner();
	 Integer getApproveStatus();
}
